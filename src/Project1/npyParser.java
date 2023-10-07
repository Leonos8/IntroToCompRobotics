package Project1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.nio.channels.FileChannel;
//import java.nio.channels.FileChannel.MapMode;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;

public class npyParser 
{ //TODO np array will be doubles, multiple by 400 to accurately create the representation
	public void read(File file)
	{
		try(RandomAccessFile f=new RandomAccessFile(file, "r");
				FileChannel channel=f.getChannel())
		{
			var header=npyHeader.read(channel);
			
			ChannelReader.read(channel, header);
			//return ChannelReader.read(channel, header);
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public class ChannelReader
	{
		private NpyArray<?> read() throws IOException{
		    long totalBytes = header.dict().dataSize();
		    int bufferSize = totalBytes > 0 && totalBytes < ((long) MAX_BUFFER_SIZE)
		      ? (int) totalBytes
		      : MAX_BUFFER_SIZE;

		    var builder = NpyArrayReader.of(header.dict());

		    var buffer = ByteBuffer.allocate(bufferSize);
		    buffer.order(header.byteOrder());
		    long readBytes = 0;
		    while (readBytes < totalBytes) {
		      int n = channel.read(buffer);
		      if (n <= 0)
		        break;
		      buffer.flip();
		      builder.readAllFrom(buffer);
		      buffer.clear();
		      readBytes += n;
		    }
		    return builder.finish();
		  }
	}
	
	public class npyHeader
	{
		public static npyHeader read(ReadableByteChannel channel) throws IOException {

		    // read the version
		    var buffer = ByteBuffer.allocate(8)
		      .order(ByteOrder.LITTLE_ENDIAN);
		    if (channel.read(buffer) < 8) {
		      throw new Exception("invalid NPY header");
		    }
		    buffer.flip();
		    var version = NpyVersion.of(buffer.array());

		    int headerLength;
		    long dataOffset;
		    buffer.position(0);
		    if (version.major == 1) {
		      buffer.limit(2);
		      if (channel.read(buffer) != 2)
		        throw new Exception("invalid NPY header");
		      buffer.flip();
		      headerLength = Util.u2ToInt(buffer);
		      dataOffset = 10 + headerLength;
		    } else {
		      buffer.limit(4);
		      if (channel.read(buffer) != 4)
		        throw new Exception("invalid NPY header");
		      long len = Util.u4ToLong(buffer);
		      dataOffset = 12 + len;
		      headerLength = (int) len;
		    }

		    // read and parse the header
		    buffer = ByteBuffer.allocate(headerLength);
		    if (channel.read(buffer) != headerLength)
		      throw new Exception("invalid NPY file");
		    var header = new String(buffer.array(), version.headerEncoding());
		    return new npyHeader(dataOffset, NpyHeaderDict.parse(header));
		  }
	}
	//var array=npyParser.read(file);
	
	/*public double[] read(Path path)
	{
        return FileChannel.open(path).use {
            var remaining = Files.size(path);
            var chunk = ByteBuffer.allocate(0);
            read(generateSequence {
                // Make sure we don't miss any unaligned bytes.
                remaining += chunk.remaining();
                if (remaining == 0L) 
                {
                } 
                else 
                {
                    val offset = Files.size(path) - remaining;
                    chunk = it.map(
                        MapMode.READ_ONLY, offset,
                        if (remaining > step) step.toLong() else remaining);

                    remaining -= chunk.capacity()
                    chunk;
                }
            })
        };
    }*/
}

package nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by sajedur on 5/19/2016.
 */
public class LockerThread extends Thread {

    public void run() {
       try {
           System.out.println(this.getName());
           RandomAccessFile aFile = new RandomAccessFile("c:/tmp/test.txt", "rw");
           FileChannel inChannel = aFile.getChannel();
           inChannel.tryLock();
           System.out.println(this.getName() + ": Locked");
           ByteBuffer buf = ByteBuffer.allocate(48);

           int bytesRead = inChannel.read(buf);
           while (bytesRead != -1) {

               System.out.println("Read " + bytesRead);
               buf.flip();

               while(buf.hasRemaining()){
                   System.out.print((char) buf.get());
               }

               buf.clear();
               bytesRead = inChannel.read(buf);
           }
           inChannel.close();
           System.out.println(this.getName() + ": Released");
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }
}

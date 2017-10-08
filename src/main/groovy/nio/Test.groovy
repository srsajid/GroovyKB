package nio

import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset

/**
 * Created by sajedur on 1/14/2016.
 */
class Test {

    public static void test1() {
        RandomAccessFile aFile = new RandomAccessFile("c:/tmp/test.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
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

        RandomAccessFile fromFile = new RandomAccessFile("c:/tmp/fromFile.txt", "rw");
        FileChannel      fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("c:/tmp/toFile.txt", "rw");
        FileChannel      toChannel = toFile.getChannel();

        long position = 0;
        long count    = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);
    }

    public static void main(String[] args) {
        new LockerThread().start()
        new LockerThread().start()
    }
}

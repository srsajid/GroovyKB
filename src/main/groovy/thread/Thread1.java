package thread;

/**
 * Created by sajedur on 2/18/2016.
 */
public class Thread1 extends Thread {
    @Override
    public void run() {
        synchronized (this) {
            try {
                System.out.print("I'm Waiting\n");
                this.wait();
                System.out.print("I'm done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package thread;

/**
 * Created by sajedur on 2/18/2016.
 */
public class Thread2 extends Thread{
    synchronized public void  run() {
        int i = 0;
        while(i < 10) {
            System.out.println(this.getName() + ": " + i);
            i++;
        }
    }
}

package thread;

class WaitNotifyTest {
    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        thread1.start();
        Thread.sleep(2000);
        System.out.print("Notifying");
        synchronized (thread1) {
            thread1.notify();
        }
    }
}

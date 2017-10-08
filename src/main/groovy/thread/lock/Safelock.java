package thread.lock;

import annotation.Test;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Safelock {
    static class Friend {
        private final String name;
        private final Lock lock = new ReentrantLock();

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public boolean impendingBow(Friend bower) {
            Boolean myLock = false;
            Boolean yourLock = false;
            try {
                myLock = lock.tryLock();
                yourLock = bower.lock.tryLock();
            } finally {
                if (!(myLock && yourLock)) {
                    if (myLock) {
                        lock.unlock();
                    }
                    if (yourLock) {
                        bower.lock.unlock();
                    }
                }
            }
            return myLock && yourLock;
        }

        public void bow(Friend bower) {
            if (impendingBow(bower)) {
                try {
                    System.out.format("%s: %s has"
                                    + " bowed to me!%n",
                            this.name, bower.getName());
                    bower.bowBack(this);
                } finally {
                    lock.unlock();
                    bower.lock.unlock();
                }
            } else {
                System.out.format("%s: %s started"
                                + " to bow to me, but saw that"
                                + " I was already bowing to"
                                + " him.%n",
                        this.name, bower.getName());
            }
        }

        public void bowBack(Friend bower) {
            System.out.format("%s: %s has" +
                            " bowed back to me!%n",
                    this.name, bower.getName());
        }
    }

    static class BowLoop implements Runnable {
        private Friend bower;
        private Friend bowee;

        public BowLoop(Friend bower, Friend bowee) {
            this.bower = bower;
            this.bowee = bowee;
        }

        public void run() {
            Random random = new Random();
            for ( ;; ) {
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                }
                bowee.bow(bower);
            }
        }
    }


    class TestClass implements Runnable {
        final Lock lock = new ReentrantLock();

        @Override
        public void run() {
            String id = "Thread-" + Thread.currentThread().getId() + ": ";
            for ( ;; ) {
                try {
                    System.out.println(id + "trying to lock");
                    lock.lock();
                    System.out.println(id + "Locked");
                    Thread.sleep(10000);
                    System.out.println(id + "trying to unlock");
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void test() {
        TestClass aClass = new TestClass();
        new Thread(aClass).start();
        new Thread(aClass).start();
    }
    public static void main(String[] args) {
//        final Friend alphonse = new Friend("Alphonse");
//        final Friend gaston = new Friend("Gaston");
//        new Thread(new BowLoop(alphonse, gaston)).start();
//        new Thread(new BowLoop(gaston, alphonse)).start();
        new Safelock().test();

    }
}
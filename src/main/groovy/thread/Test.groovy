package thread

/**
 * Created by sajedur on 2/18/2016.
 */
class Test {
    static class Shared {}
    public static check1() {
        Thread2 thread1 = new Thread2()
        Thread2 thread2 = new Thread2()
        thread1.start()
        thread2.start()
    }

    public static check2() {
        Thread thread = Thread.start {
            Thread.sleep(2 * 1000)
            println("I'm Done")
        }
        thread.join()
        println("I'm Leaving")
    }

    public static check3() {
        Shared shared = new Shared()
        Thread thread = Thread.start {
            Thread.sleep(1000)
            synchronized (shared) {
                shared.wait()
            }
            println("I'm Done Too")
        }


        Thread.start {
            Thread.sleep(2000)
            synchronized (shared) {
                shared.notifyAll()
            }
            println("I'm Done ")
            Thread.sleep(2000)
            synchronized (shared) {
                shared.notifyAll()
            }
        }

        synchronized (shared) {
            shared.wait()
        }
        println("I'm Leaving")

    }
    public static void main(String[] args) {
        check3()
    }
}

package provider

/**
 * Created by sajedur on 9/20/2015.
 */
class Main {
    public static void main(String[] args) {
        def x = ServiceLoader.load(Service.class)
        def it = x.iterator();
        while (it.hasNext()) {
            it.next().hello();
        }
        x = ServiceLoader.load(Service.class)
        it = x.iterator();
        while (it.hasNext()) {
            it.next().hello();
        }
    }
}

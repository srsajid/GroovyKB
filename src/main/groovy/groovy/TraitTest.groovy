package groovy

/**
 * Created by sajedur on 10/20/2015.
 */
class TraitTest {
    public static void main(String[] args) {
        C c = new C()
        IA ia = new C()
        IB ib = new C()

        println("Calling From: C")
        c.a()

        println("Calling From: IA")
        ia.a();

        println("Calling From: IB")
        ib.a()
    }
}

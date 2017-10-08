package inheritance.pack;


/**
 * Created by Sajid on 13-03-16.
 */
public class Test {

    public static void main(String[] args) {
//        Sub sub = new Sub();
//        sub.subClassMethod();
         new Car().print();
        new Sup();
    }
}

class Sup {
    protected Sup() {

    }
}
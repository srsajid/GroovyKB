package inheritance.pack.grov;


/**
 * Created by Sajid on 13-03-16.
 */
public class Test {

    public static void main(String[] args) {
        Sub sub = new Sub();
        sub.deMethod();
        sub = new SubSub();
        sub.deMethod();
    }
}

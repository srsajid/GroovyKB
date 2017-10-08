package lamda;


/**
 * Created by sajedur on 7/2/2015.
 */
public class LamdaTest {

    interface Function {
        void apply(String x, String y);
    }

    String x = "Hello w";

    public static void test(Function function) {
        function.apply("Hello World", "");
    }

    void ref(String x, String y) {
        System.out.println(x);
        System.out.println(this.x);
    }

    public static void main(String[] args) {
        LamdaTest ob = new LamdaTest();
        ob.getClass();
    }
}

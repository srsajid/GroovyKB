import groovy.json.JsonSlurper;
import net.glxn.qrgen.javase.QRCode;

import java.io.File;

public class JavaTest {
    public static void echo(Object s) {
        System.out.println(s);
    }
    static void varArgs(String... strings) {
        System.out.print(4);
    }
    public class InnerClass {

    }

    static class InnerClass2 {
       int x() {
            echo("Hello World");
          return 20;
        }

    }
    public static void main(String[] args) throws Exception {
//       int xdd =  new InnerClass2().x();
//        Exception x = new Exception();
//        throw x;
////        echo(x);

    }
}

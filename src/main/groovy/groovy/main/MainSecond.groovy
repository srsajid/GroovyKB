package groovy.main

import groovy.MyClass
import groovy.TestCategory
import groovy.servlet.ServletCategory
import groovy.time.TimeCategory

/**
 * Created by sajedur on 11-02-2015.
 */
class MainSecond {
    public static void main(String[] args) {
        MyClass.metaClass.metaTest = {->
            println("Tested")
        }

        def ob = new MyClass();
        ob.metaTest()
        int x = 10
        use(TimeCategory, TestCategory) {
            10.test()
            new MyClass().methodMissing("fdasf", 20, 222);
        }

        Double xx = 10.00

        def tx =  "100" as Double
        println(tx)
    }
}

package metaprogramming.category

import groovy.Test
import groovy.time.TimeCategory

/**
 * Created by sajedur on 9/10/2015.
 */
class Category {
    public static void main(String[] args) {
        groovy.time.DatumDependentDuration
        use(TestCategory) {
             1.test()
        }

        Test test = new Test("", "", 100)
        test.equals()
        println System.identityHashCode(test)
        println test.hashCode()

    }
}

package metaprogramming

import groovy.Test
import org.codehaus.groovy.runtime.metaclass.ClosureMetaClass

/**
 * Created by sajedur on 9/7/2015.
 */
class Main {

    public static void main(String[] argss) {
        def someGroovyClass = new SomeGroovyClass()
        someGroovyClass.field1
        SomeGroovyClass.metaClass.joy = "";
        someGroovyClass.metaClass.refClass = new Test("", "", 121).&test
        someGroovyClass.refClass()
        ClosureMetaClass
        someGroovyClass.x();
        assert someGroovyClass.test() == 'method exists'
        assert someGroovyClass.someMethod() == 'called invokeMethod someMethod []'
        someGroovyClass.invokeMethod()
        assert someGroovyClass.field1 == 'getHa'
        assert someGroovyClass.field2 == 'ho'
        assert someGroovyClass.field3 == 'field3'
        assert someGroovyClass.field4 == 'hu'
        someGroovyClass.field1 = "sdfdasf"
        println(someGroovyClass.field1)

        /*-----------------------------------*/
//        SomeGroovyClass.metaClass.invokeMethod = { String name, args ->
//            def metaMethod = SomeGroovyClass.metaClass.getMetaMethod(name, args)
//            def result
//            if(metaMethod) result = metaMethod.invoke(delegate,args)
//            else {
//                result = "bar"
//            }
//        }
        SomeGroovyClass.metaClass.test = {
            return "I'm from meta class"
        }
        def obj = new SomeGroovyClass()
        println(obj.test())
    }
}

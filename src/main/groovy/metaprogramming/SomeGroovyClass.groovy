package metaprogramming

/**
 * Created by sajedur on 9/7/2015.
 */
class SomeGroovyClass {

    def invokeMethod(String name, Object args) {
        return "called invokeMethod $name $args"
    }

    def methodMissing(String name, def args) {
        return "called invokeMethod $name $args"
    }

    def test() {
        return 'method exists'
    }

    def x = {
        println("Joy Bangla")
    }

    def property1 = 'ha'
    def field1 = 'hu'
    def field2 = 'ho'
    def field4 = 'hu'

    def getField1() {
        return 'getHa'
    }

    def setField1(def field1) {
        this.field1 = "field1"
    }

    def getProperty(String name) {
        if (name != 'field3')
            return metaClass.getProperty(this, name)
        else
            return 'field3'
    }
}

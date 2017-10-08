package groovy

/**
 * Created by sajedur on 8/27/2015.
 */
class MethodRef {

    def func(def add) {
        add(100)
        100
    }

    def function() {
        println "Hello World"
    }
}

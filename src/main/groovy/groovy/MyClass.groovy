package groovy

/**
 * Created by sajedur on 10-02-2015.
 */
class MyClass{

    private String a
    private String b

    private String getA() {
        return a
    }

    public MyClass(String s){
        a = "Hello World"
        println "constructing $s"
    }

    public MyClass(String a, String b){
        this.a = "Hello World"
        this.b = "Joy Bangla"
    }

    public String sayHello(String name){
        println "saying hello to $name"
        "Hello " + name //return this value
    }

    def methodMissing(String name, def args) {
        println(name + "\n" + args)
        println new Date() - 3.months
    }

    Closure x = {
        println("callded")
    }

}
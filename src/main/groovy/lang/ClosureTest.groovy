package lang

class ClosureTest {
    def outerClosure = {
        println this.class.name
        println owner.class.name
        println delegate.class.name
        def nestedClosure = {
            println this.class.name
            println owner.class.name
            println delegate.class.name
        }
        nestedClosure()
    }

    public static void main(String[] args) {
        println("********Test1**************")
        def closure = new ClosureTest()
        closure.outerClosure()

        println("********Test2**************")

        MyClass myClass = new MyClass()
        closure = new MyClass().outerClosure
        closure()
        println myClass.myString

        println("********Test3**************")

        MyClass2 myClass2 = new MyClass2()
        closure = new MyClass2().outerClosure
        closure()

        println("********Test4**************")
        closure = new MyClass3().closure
        closure.delegate = new MyOtherClass()
        closure()     // outputs: I am over in here in myOtherClass

        closure = new MyClass3().closure
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = new MyOtherClass2()
        closure()
    }
}

class MyClass {
    String myString = "myString1"
    def outerClosure = {
        println myString;     // outputs myString1
        def nestedClosure = {
            println myString;  // outputs myString1
        }
        nestedClosure()
    }
}

class MyClass2 {
    def myString = "parent class";

    def outerClosure = {
        def myString = "outerClosure";
        def nestedClosure = {
            println myString;  // outputs outerClosure
        }
        nestedClosure()
    }
}


class MyOtherClass {
    String myString = "I am over in here in myOtherClass"
}

class MyOtherClass2 {
    String myString = "I am over in here in myOtherClass2"
}

class MyClass3 {
    String myString = "Kopa"
    def closure = {
        println myString
    }
}


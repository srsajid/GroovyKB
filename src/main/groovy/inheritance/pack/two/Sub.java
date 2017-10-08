package inheritance.pack.two;

import inheritance.pack.one.Super;

public class Sub extends Super{
    void subClassMethod() {
        pbMethod();
        prMethod();
//        deMethod(); // cant call
        pbVar = "54";
        prVar = "54545";
        proxy();
    }
}

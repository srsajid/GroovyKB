package oop.generics;

public class Box<T, T2> {
    T t;

    void set(T t1) {
        this.t = t1;
    }

    T get() {
        return t;
    }

    public <U extends Number> void inspect(U u){
        System.out.println("T: " + t.getClass().getName());
        System.out.println("U: " + u.getClass().getName());
    }
}

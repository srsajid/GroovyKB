package oop.generics;

public class Main {
    public static void main(String[] args) {
        Box<String, Integer> box = new Box<>();

        box.set("ssss");

        String string = box.get();
    }
}

package design.patterns.Facade

class Rectangle implements Shape {

   @Override
   public void draw() {
       System.out.println("Shape: Rectangle");
   }
}
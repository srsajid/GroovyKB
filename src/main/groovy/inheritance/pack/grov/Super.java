package inheritance.pack.grov;

/**
 * Created by Sajid on 13-03-16.
 */
public class Super {

    private static void iAmStatic() {
        System.out.println("I'm Private Static Method");
    }

    String deVar;

    private String pvVar;

    protected String prVar;

    public String pbVar;

    private void pvMethod() {
        System.out.println("PV Method");
    }

    void deMethod() {
        iAmStatic();
        System.out.println("DE Method");
    }

    protected void prMethod() {
        System.out.println("PR Method");
    }

    public void pbMethod() {
        System.out.println("PB Method");
    }

    public void proxy() {
        pvMethod();
    }

}


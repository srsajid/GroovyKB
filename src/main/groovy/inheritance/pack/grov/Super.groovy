package inheritance.pack.grov;

/**
 * Created by Sajid on 13-03-16.
 */
trait Super {

    static String _x
    static String getX() {
        _x = "I'm Private Static Method"
        return _x;
    }

    String deVar;

    private String pvVar;

    protected String prVar;

    public String pbVar;

    private void pvMethod() {
        System.out.println("PV Method");
    }

    void deMethod() {
        println(x)
        System.out.println("DE Method");
    }

    void prMethod() {
        System.out.println("PR Method");
    }

    public void pbMethod() {
        System.out.println("PB Method");
    }

    public void proxy() {
        pvMethod();
    }

}


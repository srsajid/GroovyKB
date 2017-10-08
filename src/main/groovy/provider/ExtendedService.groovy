package provider

/**
 * Created by sajedur on 9/20/2015.
 */
class ExtendedService implements Service{
    ExtendedService() {
        println("Init")
    }

    @Override
    public void hello() {
        System.out.print("Hello Ex \n");
    }
}

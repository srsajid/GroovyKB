package design.patterns.Observer;

/**
 * Created by sajedur on 2/16/2017.
 */
public class OctalObserve extends Observer {

    public OctalObserve(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println( "Octal String: " + Integer.toOctalString( subject.getState() ) );
    }
}
package serial.com;

public class SMSTest {

    static send(SMS sms) {
        File contactFile = new File("C:\\Users\\srsaj\\Desktop\\Bulk\\phase-1.csv")
        String mgs = "Please cast your Vote with bringing any photo ID at Azimpur Govt Girls School & College\nRegards\nAfifuzzaman Ezaz\nCouncilor\nBallot- 52\n(Dr.Sohel-Tofayel panel)";
        String failed = ""
        contactFile.eachLine {

            Boolean result = sms.send(it.trim(), mgs)
            if(!result) {
                try {
                    sms.closePort()
                    Thread.sleep(10000)
                    sms.openPort()
                } catch (Exception ex) {
                    println(failed)
                    throw  ex
                }
                failed += it + "\n";
            }
        }
        println(failed)
    }

    static balance(SMS sms) {
        sms.ussd("*121*1*2#")
        Thread.sleep(5000)
        sms.read()
    }

    public static void main(String[] args) {
        SMS sms = new SMS("COM3")
        sms.read()
        sms.closePort()
    }
}

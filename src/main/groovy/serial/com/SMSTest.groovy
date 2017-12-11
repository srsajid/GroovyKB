package serial.com;

public class SMSTest {

    static send(SMS sms) {
        File contactFile = new File("C:\\Users\\srsaj\\Desktop\\Bulk\\Bulk-Mobile-number.csv")
        String mgs = "Dear concern,\nTo make BCS more beneficial toward ICT professionals, please cast your vote\n\"Afifuz Zaman Ezaz\"\nCouncilor (Dr.Sohel - Tofayel) panel\nBallot#52";
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
        SMS sms = new SMS("COM7")
        sms.send("01672178618", "HEllo")
        sms.closePort()
    }
}

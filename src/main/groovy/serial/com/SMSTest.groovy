package serial.com;

public class SMSTest {

    static send(SMS sms) {
        File contactFile = new File("C:\\Users\\srsaj\\Desktop\\Bulk\\prof.csv")
        String mgs = "Please cast your vote for Vice President(Admin)Prof Dr Mohammad Shorif Uddin,Ex Chair, CSE Jahangirnagar University from Dr Sohel-Tofayel Panel in BCS Election";
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
        SMS sms = new SMS("COM14")
        send(sms)
        sms.closePort()
    }
}

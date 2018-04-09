package serial.com

import util.DB;

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

    synchronized static Boolean send(String number, String message) {
        Runtime runtime = Runtime.getRuntime()
        String[] commands = ["D:\\IdeaProjects\\GroovyKB\\src\\main\\resources\\SMSSender.exe", number, message]
        Process process = runtime.exec(commands)
        String response = process.getInputStream().text
        print(response)
        return response.endsWith("\r\nOK\r\n\r\n") || response.endsWith("\r\nOK\r\n") || response.endsWith("\r\nOK")
    }

    static void generateSubscriber() {
        HashSet<String> numbers = new HashSet<String>();
        DB db = new DB("startech")
        List customers = db.getResult("select telephone from sr_customer") + db.getResult("select telephone from sr_order")
        db = new DB("test")
        db.getResult("TRUNCATE `sms_subscriber`")
        db.insert("insert IGNORE into sms_subscriber set number = ?", ["01747184891"])
        db.insert("insert IGNORE into sms_subscriber set number = ?", ["01672178618"])
        db.insert("insert IGNORE into sms_subscriber set number = ?", ["01709995551"])
        customers.each { Map customer ->
            String number = customer.telephone.trim().replaceAll(/\s/, "")
            if(number.startsWith("+88")) {
                number = number.substring(3, number.length())
            }
            if(number.length() == 11 && (number.startsWith("017") || number.startsWith("018") || number.startsWith("019") || number.startsWith("016") || number.startsWith("015")) ) {
                db.insert("insert IGNORE into sms_subscriber set number = ?", [number])
            }
        }
    }
    public static void main(String[] args) {
        DB db = new DB("test")
        String message = "Enjoy exciting offers of Pohela Boisakh @STAR TECH, get FREE HOME DELIVERY inside Dhaka by using promo code: 1425\nDetails:https://goo.gl/FhBGUh"
        List subscribers = db.getResult("select * from sms_subscriber where sent = 0")
        subscribers.each {
            if(send(it.number, message)) {
                db.getResult("update sms_subscriber set sent = 1 where id = ${it.id}")
            } else {
                Thread.sleep(5000)
            }

        }
    }
}

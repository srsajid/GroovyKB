import net.glxn.qrgen.javase.QRCode

/**
 * Created by Sajid on 07-03-17.
 */
class NXT {
    static members = [
            "01": "Sajid",
            "02": "Arif",
            "03": "Jahid",
            "04": "Rabiul",
            "05": "Shahadat",
            "06": "Rafiq"
    ]
    public static void main(String[] args) {
        String key = "605aa70effd2c6374823b54bbc560b99";
        String month = "04"
        String monthName = "April"
        6.times {
            String memberId = "0" + (it + 1)
            String message = memberId + month + "17"
            String chiper = AES.encrypt(message, key)
            println AES.decrypt(chiper, key)
            File file = QRCode.from(chiper).withSize(250, 250).file();
            file.renameTo(new File("D:\\nxt\\${monthName}-${members[memberId]}.jpg"))
        }
    }
}

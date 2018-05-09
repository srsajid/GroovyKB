package startech.report

import startech.LocalDatabaseUpdater
import util.DB

class PcBuilderUser {
    static void main(String[] args) {
        DB db = new DB('startech')
        List list = db.getResult("select concat(q.firstname, ' ', q.lastname) as name, q.email, q.telephone from sr_quote q where date(q.date_added) > date('2018-04-01')") +
                db.getResult("select concat(c.firstname, ' ', c.lastname) as name, c.email, c.telephone from sr_pc p left join sr_customer c on p.customer_id = c.customer_id where date(p.date_added) > date('2018-04-01')")
        File report = new File("c:\\MyDrive\\report.csv")
        report.append("name,email,phone\r\n")
        list.each {
            String phone = it.telephone
            if(!phone.startsWith("+88") && !phone.startsWith("0088")) {
                phone = "+88" + phone
            }
            report.append("${it.name},${it.email},${phone}\r\n")
        }
    }
}

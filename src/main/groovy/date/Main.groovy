package date

import javax.xml.crypto.Data
import java.text.DateFormat
import java.text.SimpleDateFormat;

/**
 * Created by sajedur on 11/2/2015.
 */
public class Main {

    public static test() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

        Date lastDayOfMonth = cal.getTime()
        println(lastDayOfMonth)

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyddMMHHmmssSSS000")
        Date date = new Date();
        int tzOffsetMin = (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET))/(1000*60);
        def x = -20
        println dateFormat.format(date)
    }

    public static createDateFromStringWithTimeZone() {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("GMT+6:30")
        isoFormat.setTimeZone(timeZone);
        Date date = isoFormat.parse("2010-05-23 09:01:02");
        println(date)
    }


    public static test2() {
        Date date = new Date()//new Date(1317872105000L)
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        calendar.setTime(date);

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        System.out.println(formatter.format(calendar.getTime()));
    }
    public static void main(String[] args) {
        test2()
        createDateFromStringWithTimeZone()
    }
}

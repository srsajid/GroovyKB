package date

import java.time.format.*
import java.time.*
import java.time.temporal.*

/**
 * Created by sajedur on 6/23/2016.
 */
class DateTimeAPI {

    public static test1() {
        DayOfWeek dow = DayOfWeek.MONDAY;
        Locale locale = Locale.getDefault();
        System.out.println(dow.getDisplayName(TextStyle.FULL, locale));
        System.out.println(dow.getDisplayName(TextStyle.NARROW, locale));
        System.out.println(dow.getDisplayName(TextStyle.SHORT, locale));
        System.out.printf("%s%n", DayOfWeek.MONDAY.plus(3));
    }

    public static test2() {
        Locale locale = Locale.getDefault();
        Month month = Month.JUNE;
        System.out.println(month.getDisplayName(TextStyle.FULL, locale));
        System.out.println(month.getDisplayName(TextStyle.NARROW, locale));
        System.out.println(month.getDisplayName(TextStyle.SHORT, locale));
    }

    public static test3() {
        LocalDate date = LocalDate.of(2000, Month.NOVEMBER, 20);
        LocalDate nextWed = date.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        println(nextWed)
        DayOfWeek dotw = LocalDate.of(2012, Month.JULY, 9).getDayOfWeek();
    }

    public static test4() {
        YearMonth date = YearMonth.now();
        System.out.printf("%s: %d%n", date, date.lengthOfMonth());

        YearMonth date2 = YearMonth.of(2010, Month.FEBRUARY);
        System.out.printf("%s: %d%n", date2, date2.lengthOfMonth());

        YearMonth date3 = YearMonth.of(2012, Month.FEBRUARY);
        System.out.printf("%s: %d%n", date3, date3.lengthOfMonth());
    }

    public static test5() {
        MonthDay date = MonthDay.of(Month.FEBRUARY, 29);
        boolean validLeapYear = date.isValidYear(2010);
        Year.of(2012).isLeap();
    }

    static test6() {
        Date date = new Date()
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault()
        ZoneOffset offset = zoneId.getRules().getOffset(localDateTime)
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, offset)
        println(offsetDateTime.withOffsetSameInstant(ZoneOffset.of("+00:00")))
    }

    static test7() {
        Date date = new Date()
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.toInstant()
        ZoneId zoneId = ZoneId.systemDefault()
        ZoneOffset offset = zoneId.getRules().getOffset(localDateTime)
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId)
        println(zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")))
        println(offset)
    }

    static test8() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a");
        LocalDateTime leaving = LocalDateTime.of(2013, Month.JULY, 20, 19, 30);
        ZoneId leavingZone = ZoneId.of("America/Los_Angeles");
        ZonedDateTime departure = ZonedDateTime.of(leaving, leavingZone);

        try {
            String out1 = departure.format(format);
            System.out.printf("LEAVING:  %s (%s)%n", out1, leavingZone);
        } catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", departure);
            throw exc;
        }
        ZoneId arrivingZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime arrival = departure.withZoneSameInstant(arrivingZone)
                .plusMinutes(650);

        try {
            String out2 = arrival.format(format);
            System.out.printf("ARRIVING: %s (%s)%n", out2, arrivingZone);
        } catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", arrival);
            throw exc;
        }

        if (arrivingZone.getRules().isDaylightSavings(arrival.toInstant()))
            System.out.printf("  (%s daylight saving time will be in effect.)%n",
                    arrivingZone);
        else
            System.out.printf("  (%s standard time will be in effect.)%n",
                    arrivingZone);

    }

    static test9() {
        Instant timestamp = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault()
        ZoneOffset offset = ZoneOffset.ofHours(0)
        //zoneId.getRules().getOffset(localDateTime)
        println localDateTime.toInstant(offset)
        println(timestamp)
    }

    public static void main(String[] args) {

    }
}

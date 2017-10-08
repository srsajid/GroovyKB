package loacalization

import java.text.NumberFormat

/**
 * Created by sajedur on 3/14/2016.
 */
class Test {
    public static void main(String[] args) {
        //listPath(new File("C:\\Intel\\Logs"));
        //getNumberFormat("$");
        System.out.println(getLocale("BDT"));

    }
    private static Locale getLocale(String strCode) {

        for (Locale locale : NumberFormat.getAvailableLocales()) {
            String code = NumberFormat.getCurrencyInstance(locale).getCurrency().getCurrencyCode();
            if (strCode.equals(code)) {
                return locale;
            }
        }
        return null;
    }
}

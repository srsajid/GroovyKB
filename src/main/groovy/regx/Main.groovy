package regx

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by sajedur on 6/21/2015.
 */
class Main {

    public static test1() {
        String text    =
                "This is the text to be searched " +
                        "for occurrences of the http:// pattern.";

        String patternString = ".*http://.*";

        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(text);
        System.out.println("matches   = " + matcher.matches());
    }

    public static test2() {
        String text    =
                "This is the text to be searched " +
                        "for occurrences of the http:// pattern.";

        String patternString = "This is the";

        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        System.out.println("lookingAt = " + matcher.lookingAt());
        System.out.println("matches   = " + matcher.matches());
    }

    public static test3() {
        String text    =
                "This is the text which is to be searched " +
                        "for occurrences of the word 'is'.";

        String patternString = "is";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        int count = 0;
        while(matcher.find()) {
            count++;
            System.out.println("found: " + count + " : "
                    + matcher.start() + " - " + matcher.end());
        }
    }

    public static test4() {
        String text    =
                "John writes about this, and John writes about that," +
                        " and John writes about everything. "
        ;

        String patternString1 = "(John)";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            System.out.println("found: " + matcher.group(1));
        }
    }

    public static test5() {
        String text    =
                "John writes about this, and John Doe writes about that," +
                        " and John Wayne writes about everything."
        ;

        String patternString1 = "(John) (.+?) ";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            System.out.println("found: " + matcher.group(1) +
                    " "       + matcher.group(2));
        }
    }

    public static test6() {
        String text    =
                "John writes about this, and John Doe writes about that," +
                        " and John Wayne writes about everything. "
        ;

        String patternString1 = "((John) (.+?)) ";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            System.out.println("found: <"  + matcher.group(1) +
                    "> <"       + matcher.group(2) +
                    "> <"       + matcher.group(3) + ">");
        }
    }

    public static test7() {
        String text    =
                "John writes about this, and John Doe writes about that," +
                        " and John Wayne writes about everything."
        ;

        String patternString1 = "((John) (.+?)) ";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(text);

        String replaceAll = matcher.replaceAll("Joe Blocks ");
        System.out.println("replaceAll   = " + replaceAll);

        String replaceFirst = matcher.replaceFirst("Joe Blocks ");
        System.out.println("replaceFirst = " + replaceFirst);
    }

    public static test8() {
        String text =
                "John writes about this, and John Doe writes about that, and John Wayne writes about everything."
        ;

        String patternString1 = "((John) (.+?)) ";

        Pattern      pattern      = Pattern.compile(patternString1);
        Matcher      matcher      = pattern.matcher(text);
        StringBuffer stringBuffer = new StringBuffer();

        while(matcher.find()){
            matcher.appendReplacement(stringBuffer, "Joe Blocks ");
            System.out.println(stringBuffer.toString());
        }
        matcher.appendTail(stringBuffer);

        System.out.println(stringBuffer.toString());
    }

    public static void main(String[] args) {
        8.times { it ->
            "test${it+1}"()
        }

    }
}

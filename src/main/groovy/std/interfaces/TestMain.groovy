package std.interfaces

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by sajedur on 5/26/2015.
 */
class TestMain {
    public static void main(String[] args) {
        String line = "/*color: #dfafsdff */";
        Pattern pattern = Pattern.compile("\\/\\*.*color:(.+)\\*\\/");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            System.out.println("group 1: " + matcher.group(1));
            System.out.println("group 2: " + matcher.group(2));
        }
    }
}

package groovy.main

import groovy.Test
import org.codehaus.groovy.runtime.MethodClosure

import java.util.regex.Matcher
import java.util.regex.Pattern

public class Main {
    public static void x() {
        println(10)
    }

    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in)
        String str = scanner.next()
        String aqlRegx = /^(?i)select (.+) from ([A-z]+)( where .+)?( group by .+)?$/
        String ALIASED_COLUMN_REGEX = /^(?i)(.+)\s+as\s+'(.+)'$/
        while (true) {
            Matcher matcher = str =~ ALIASED_COLUMN_REGEX
            if(matcher.find()) {
                println(matcher.group(1))
                println(matcher.group(2))
            } else {
                println("Not Matched")
            }
            str = scanner.nextLine()
            str = str.trim();
            println(str)
        }

    }

}

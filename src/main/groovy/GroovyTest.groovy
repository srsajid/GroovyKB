import groovy.C
import groovy.MyClass
import groovy.io.FileType
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import hibernate.Employee
import log4j.Main
import org.apache.logging.log4j.core.util.FileUtils
import org.codehaus.groovy.runtime.GStringImpl
import org.codehaus.groovy.runtime.callsite.CallSite
import org.springframework.util.FileCopyUtils
import zip.ZipUtil

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Created by sajedur on 10/20/20
 */
class GroovyTest {
    static C _a = new C()
    static C c  = null



    static void test() {
        _a = _a ?: new C()
        Boolean x = _a != null && c == null
    }

    public static void icon() {
        File file = new File("D:\\codes\\extreme\\extreme2\\ref\\plugins\\snippet\\web-app\\images\\icons")
        Map map = [:]
        file.eachFile(FileType.FILES, { File file1->
            String key = file1.name.split("\\.")[0]
            map[key] = file1.name
        });
        println JsonOutput.toJson(map)
    }

    public static void copyMS() {
        File ms = new File("d:\\ms")
        ms.deleteDir()
        ms.mkdirs()
        File plugins = new File("D:\\codes\\extreme\\extreme2\\ref\\plugins");
        def copier = { File plugin, String prefix = "\\plugins\\" ->
            File toCopy = new File(plugin, "grails-app\\i18n\\messages.properties")
            if(toCopy.exists()) {
                new File("d:\\ms${prefix}\\${plugin.name}\\server-side").mkdirs()
                FileCopyUtils.copy(toCopy, new File("d:\\ms${prefix}\\${plugin.name}\\server-side\\messages.properties"))
            }
            toCopy = new File(plugin, "web-app\\js\\i18n\\messages.properties")
            if(toCopy.exists()) {
                new File("d:\\ms${prefix}\\${plugin.name}\\client-side").mkdirs()
                FileCopyUtils.copy(toCopy, new File("d:\\ms${prefix}\\${plugin.name}\\client-side\\messages.properties"))
            }

        }
        copier(new File("D:\\codes\\extreme\\extreme2"), "")
        plugins.eachDir copier
    }

    static check() {
        def  cMil = System.currentTimeMillis()
        for (int i = 0; i < 500; i++) {
            test()
        }
        println(System.currentTimeMillis() - cMil)
    }

     def y(Closure closure) {
        String str = "Joy bangla"
//        closure.setProperty("myProp", """Hello World""")
//        closure.delegate = new Test()
        def x = closure(str)
        println()
    }

    def y() {
        def c1 = {
            println()
            def c11 = {
                println()
            }
            c11()
        }
        c1()
    }

    def x () {
        String str = "Hello World"
        def closure = {
            println(str)
            return this.y()
        }
        y(closure)
    }

    static z() {
        def c1 = {
            println()
            def c11 = {
                println()
            }
            c11()
        }
        c1.delegate = null
        c1()
    }

    static void main(String[] args) {
    }
}

class Test {
    String str;
    public Test() {
        str = "Gang bang"
    }
}
class Enclosing {
    void run() {
        def whatIsThisObject = { getThisObject() }
        assert whatIsThisObject() == this
        def whatIsThis = { this }
        assert whatIsThis() == this
    }
}

class EnclosedInInnerClass {
    class Inner {
        Closure cl = { this }
    }
    void run() {
        def inner = new Inner()
        assert inner.cl() == inner
    }
}

class NestedClosures {
    void run() {
        def nestedClosures = {
            def cl = { this }
            cl()
        }
        assert nestedClosures() == this
    }
}
import groovy.C
import groovy.MyClass
import groovy.io.FileType
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import hibernate.Employee
import http.HttpUtil
import log4j.Main
import org.apache.commons.lang.StringEscapeUtils
import org.apache.logging.log4j.core.util.FileUtils
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.codehaus.groovy.runtime.GStringImpl
import org.codehaus.groovy.runtime.callsite.CallSite
import org.springframework.util.FileCopyUtils
import org.springframework.web.util.UriUtils
import util.DB
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
    static C c = null


    static void test() {
        _a = _a ?: new C()
        Boolean x = _a != null && c == null
    }

    public static void icon() {
        File file = new File("D:\\codes\\extreme\\extreme2\\ref\\plugins\\snippet\\web-app\\images\\icons")
        Map map = [:]
        file.eachFile(FileType.FILES, { File file1 ->
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
            if (toCopy.exists()) {
                new File("d:\\ms${prefix}\\${plugin.name}\\server-side").mkdirs()
                FileCopyUtils.copy(toCopy, new File("d:\\ms${prefix}\\${plugin.name}\\server-side\\messages.properties"))
            }
            toCopy = new File(plugin, "web-app\\js\\i18n\\messages.properties")
            if (toCopy.exists()) {
                new File("d:\\ms${prefix}\\${plugin.name}\\client-side").mkdirs()
                FileCopyUtils.copy(toCopy, new File("d:\\ms${prefix}\\${plugin.name}\\client-side\\messages.properties"))
            }

        }
        copier(new File("D:\\codes\\extreme\\extreme2"), "")
        plugins.eachDir copier
    }

    static check() {
        def cMil = System.currentTimeMillis()
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

    def x() {
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
        println    "Basic " + Base64.getEncoder().encodeToString("drongo:5UG7VGlaEVMMyFjLA7jmEK1NiMWigk2P5aJcsFNxDg0bZB8Hbfr5Uiheg".getBytes());
    }

    static attr() {
        DB db = new DB("mutho_phone")
        List<Map> groups = db.getResult("select * from sr_attribute_group ag left join sr_attribute_group_description agd on ag.attribute_group_id = agd.attribute_group_id where ag.attribute_profile_id = 2 order by sort_order")
        List<Map> results = []
        groups.each { group ->
            List<Map> attrs = db.getResult("select * from sr_attribute ag left join sr_attribute_description agd on ag.attribute_id = agd.attribute_id where ag.attribute_group_id = ${group.attribute_group_id}")
            attrs.each {
                results.add([
                        attribute_id  : it.attribute_id,
                        group_name    : group.name,
                        attribute_name: it.name,
                        maping        : ""
                ])
            }
        }
        println(JsonOutput.toJson(results))

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
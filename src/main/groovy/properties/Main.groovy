package properties

import org.omg.CORBA.portable.OutputStream

/**
 * Created by sajedur on 1/6/2016.
 */
class Main {
    public static void main(String[] args) {
        File a = new File("D:\\codes\\GroovyKB\\src\\main\\groovy\\properties\\a.properties")
        File b = new File("D:\\codes\\GroovyKB\\src\\main\\groovy\\properties\\b.properties")
        Properties properties = new Properties()
        a.withInputStream {
            properties.load(it)
        }
        b.withInputStream {
            properties.load(it)
        }
        a.withInputStream {
            properties.load(it)
        }
        def file =  new File("D:\\codes\\GroovyKB\\src\\main\\groovy\\properties\\d.properties")
        properties.store(new FileOutputStream(file), "")
        println(properties)
    }
}

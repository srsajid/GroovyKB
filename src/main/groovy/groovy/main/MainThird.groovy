package groovy.main

import groovy.xml.MarkupBuilder

/**
 * Created by sajedur on 01-03-2015.
 */
class MainThird {

    public static void main(String[] args) {
        def comment = "<![CDATA[<!-- address is new to this release -->]]>"
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        def writer = new FileWriter("person.xml")
        writer << builder.bind {

            person {
                firstname("Jane")
                lastname("Doe")
                address("123 Main")
                [10, 20].each {

                }
            }
        }
    }
}

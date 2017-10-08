package benchmark

import org.codehaus.groovy.grails.web.pages.FastStringWriter

import java.nio.Buffer

class StringWriterVsBuilder {
    public static void main(String[] args) {
        /*String Builder*/
        Long start = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder()
        for (int i = 0; i < 10000000; i++) {
            builder.append(i)
        }
        builder.toString()
        Long stop = System.currentTimeMillis();
        System.out.println("Builder: " + (stop - start) + "ms");

        /*String Writer*/
        start = System.currentTimeMillis();
        StringWriter writer = new StringWriter()
        for (int i = 0; i < 10000000; i++) {
            writer.write(i)
        }
        writer.toString()
        writer.close()
        stop = System.currentTimeMillis();
        System.out.println("Writer: " + (stop - start) + "ms");

        /*String Writer*/
        start = System.currentTimeMillis();
        FastStringWriter fastStringWriter = new FastStringWriter()
        for (int i = 0; i < 10000000; i++) {
            fastStringWriter.write(i)
        }
        writer.toString()
        writer.close()
        stop = System.currentTimeMillis();
        System.out.println("Fast String Writer: " + (stop - start) + "ms");


        /*Constructors*/
        println("------------------Constructor-----------------------")
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++)
        StringBuilder builder2 = new StringBuilder()
        stop = System.currentTimeMillis();
        System.out.println("String Builder: " + (stop - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++)
            StringWriter stringWriter1 = new StringWriter()
        stop = System.currentTimeMillis();
        System.out.println("String Writer: " + (stop - start) + "ms");


        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++)
        FastStringWriter fastStringWriter1 = new FastStringWriter()
        stop = System.currentTimeMillis();
        System.out.println("Fast String Writer: " + (stop - start) + "ms");

        /*Convert To String*/
        println("------------------Convert To String-----------------------")
        start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++)
            builder.toString()
        stop = System.currentTimeMillis();
        System.out.println("String Builder: " + (stop - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++)
            writer.toString()
        stop = System.currentTimeMillis();
        System.out.println("String Writer: " + (stop - start) + "ms");


        start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++)
            fastStringWriter.toString()
        stop = System.currentTimeMillis();
        System.out.println("Fast String Writer: " + (stop - start) + "ms");


        /*Final*/
        println("------------------Final-----------------------")
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            StringBuilder test = new StringBuilder()
            for (int j = 0; j < 50; j++) {
                test.append(j)
            }
            test.toString()
        }
        stop = System.currentTimeMillis();
        System.out.println("String Builder: " + (stop - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++){
            StringWriter test = new StringWriter()
            for (int j = 0; j < 50; j++) {
                test.write(j)
            }
            test.toString()
        }
        stop = System.currentTimeMillis();
        System.out.println("String Writer: " + (stop - start) + "ms");


        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++){
            FastStringWriter test = new FastStringWriter()
            for (int j = 0; j < 50; j++) {
                test.write(j)
            }
            test.toString()
        }
        stop = System.currentTimeMillis();
        System.out.println("Fast String Writer: " + (stop - start) + "ms");
    }
}

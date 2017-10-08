package String

class StringWriter {
    public static void main(String[] args) {
        java.io.StringWriter writer = new java.io.StringWriter();
        writer.write('1')
        writer.write('1')
        writer.write('1')
        println writer.toString()
        writer.flush()
        writer.write('2')
        println writer.toString()
        writer.close()
        writer.write('6565')
        println writer.toString()

    }
}

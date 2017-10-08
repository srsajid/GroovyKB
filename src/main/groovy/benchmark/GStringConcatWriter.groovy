package benchmark

class GStringConcatWriter {
    static testUnit(int N, int n) {
        Map map = [gString: 0, concat: 0, writer: 0]

        for (int i = 0; i < N ; i++) {
            String one = "OneOneOneOne"
            String two = "TwoTwoTwoTwo"
            String three = "ThereThereThere"
            String four = "FourFourFourFourFour"
            String five
            StringWriter writer = new StringWriter()

            Long current = System.currentTimeMillis()
            for(int j = 0; j < n; j++) {
                five = "${one} ${two} $three $four"
            }
            map.gString = map.gString + (System.currentTimeMillis() - current)

            current = System.currentTimeMillis()
            for(int j = 0; j < n; j++) {
                five = one  + two + " " + three + four
            }
            map.concat = map.concat + (System.currentTimeMillis() - current)

            current = System.currentTimeMillis()
            for(int j = 0; j < n; j++) {
                writer << one
                writer << two
                writer << " "
                writer << " "
                writer << three
                writer << four
            }
            map.writer = map.writer + (System.currentTimeMillis() - current)
        }

        println("GString: " + map.gString / N)
        println("Concat: " + map.concat / N)
        println("Writer: " + map.writer / N)
    }

    public static void main(String[] args) {
        testUnit(10, 100000)
        println("---------------------")

        testUnit(10, 1000000)
        println("---------------------")

        testUnit(10, 1000000)
        println("---------------------")

        testUnit(10, 1000000)
        println("---------------------")

        testUnit(10, 1000000)
        println("---------------------")

        testUnit(10, 1000000)
        println("---------------------")

        testUnit(10, 1000000)
        println("---------------------")

        testUnit(10, 1000000)
        println("---------------------")

        testUnit(10, 1000000)
        println("---------------------")

    }
}

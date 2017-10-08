package String
/**
 * Created by sajedur on 10/20/2015.
 */
class Main {

    public static void main(String[] args) {
        Closure closure = { List s, b ->
            println(s)
        }
        StringBuilder  what = new StringBuilder('fence')
        def text = "The cow jumped over the $what"
        println text
        what.replace(0, 5, "moon")
        println text
        closure.call([["dasf"], "fasf"])

        String str = "It's a rainy day in Seattle"
        println str
        str -= "rainy"
        println str

        def pattern = ~"(G|g)roovy"
        text = 'Groovy is Hip'
        if (text =~ pattern)
            println "match"
        else
            println "no match"
        if (text ==~ pattern)
            println "match"
        else
            println "no match"
    }
}

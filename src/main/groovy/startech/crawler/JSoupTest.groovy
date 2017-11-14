package startech.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.jsoup.nodes.Element


public class JSoupTest {
    static List getAllProductURLs(Document categoryDoc) {
        List<String> productURLs = []
        Elements products = categoryDoc.select(".products-grid > li");
        for (Element product : products) {
            Element name = product.select(".product-name a")[0]
            if(name) {
                productURLs.add(name.attr("href"))
            }
        }
        return productURLs
    }

    static void parseCategory() {
        List productURLs = []
        Document doc = Jsoup.connect("https://ryanscomputers.com/network/access-point/tp-link-tl-wa850re-300mbps-universal-wireless-range-extender.html").get();
        while (doc) {
            productURLs.addAll(getAllProductURLs(doc))
            Element nextPage = doc.select(".pages .next.i-next")[0]
            String nextPageURL = nextPage ? nextPage.attr("href") : null
            doc = nextPageURL ? Jsoup.connect(nextPageURL).get() : null
        }
    }

    public static void main(String[] args) {
        Document productDoc = Jsoup.connect("https://ryanscomputers.com/network/access-point/tp-link-tl-wa850re-300mbps-universal-wireless-range-extender.html").get();
        Elements specs = productDoc.select("#product-attribute-specs-table tr");
        String model = null
        specs.each {Element spec ->
            String label = spec.select(".label")[0]?.text()?.trim()
            String value = spec.select(".data")[0]?.text()?.trim()
            if(label == "Model") {
                model = value
            }
        }
        println(model)
    }
}

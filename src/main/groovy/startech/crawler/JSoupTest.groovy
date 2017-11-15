package startech.crawler

import org.apache.commons.lang.StringEscapeUtils
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.jsoup.nodes.Element
import util.DB

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


public class JSoupTest {
    static db = new DB("price_comapre");

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

    static crawlProduct(String productUrl) {
        Document productDoc = Jsoup.connect(productUrl).get();
        Elements specs = productDoc.select("#product-attribute-specs-table tr");
        String name = productDoc.select(".product-name h2")[0]?.text()?.trim()
        String code = productDoc.select(".productsku span")[0]?.text()?.trim()
        String price = productDoc.select(".regular-price .price")[0]?.text()?.trim()
        price = price ?: productDoc.select(".special-price .price")[0]?.text()?.trim()
        if(price) {
            price = price.replaceAll("[A-Za-z,]", "")
        }
        String model = ""
        Iterator<Element> iter = specs.iterator()
        while (iter.hasNext()) {
            Element spec = iter.next();
            String label = spec.select(".label")[0]?.text()?.trim()
            String value = spec.select(".data")[0]?.text()?.trim()
            if(label == "Model") {
                model = value
                break
            }
        }

        db.insert("INSERT INTO `ryans_product` (`name`, `code`, `model`, `url`, `price`) VALUES (?, ?, ?, ?, ?)", [name, code, model, productUrl, price])
    }

    static void crawlCategory(String categoryURL) {
        List<String> productURLs = []
        Document doc = Jsoup.connect(categoryURL).get();
        while (doc) {
            productURLs.addAll(getAllProductURLs(doc))
            Element nextPage = doc.select(".pages .next.i-next")[0]
            String nextPageURL = nextPage ? nextPage.attr("href") : null
            doc = nextPageURL ? Jsoup.connect(nextPageURL).get() : null
        }
        productURLs.each {
            try {
                crawlProduct(it)
            } catch (Exception ex) {
                println("Product URL: "  + it + "\nMessage: "  + ex.message + "\n----------------------------------------------")
            }
        }
    }


    public static void main(String[] args) {
        StringEscapeUtils.escapeJavaScript("'")
        Document doc = Jsoup.connect("https://ryanscomputers.com/").get();
        Elements menus = doc.select("ul.sm-megamenu-hover.sm_megamenu_menu > li.other-toggle")
        menus.remove(0)
        List<String> categoryURLs = []
        menus.each {
            it.select("a").each {
                String url = it.attr("href").trim()
                if (url.startsWith("http")) {
                    categoryURLs.add( url)
                }
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);

        categoryURLs.each { url ->
            executor.execute({
                try {
                    crawlCategory(url)
                } catch (Exception ex) {
                    println("Category URL: "  + url + "\nMessage: "  + ex.message + "\n----------------------------------------------")
                }
            })
        }
    }
}

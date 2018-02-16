package startech.crawler

import org.jsoup.helper.SRHttpConnection
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import thread.pool.MyMonitorThread
import util.DB

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

public class Pickaboo {
    static db = new DB("price_compare");
    static ConcurrentHashMap<String, Integer> failedCount = new ConcurrentHashMap<String, Integer> ()

    static List getAllProductURLs(Document categoryDoc) {
        List<String> productURLs = []
        Elements products = categoryDoc.select(".category-products .products-grid .product-column");
        for (Element product : products) {
            Element name = product.select(".product-name a")[0]
            if(name) {
                productURLs.add(name.attr("href"))
            }
        }
        return productURLs
    }

    static crawlProduct(String productUrl) {
        Document productDoc = SRHttpConnection.connect(productUrl).get();
        String name = productDoc.select(".em-product-view-secondary [itemprop=name]")[0]?.text()?.trim()
        String code = productDoc.select("#product_addtocart_form [name=product]").val()?.trim()
        String price = productDoc.select(".em-product-view-secondary [itemprop=price]")[0]?.attr("content")?.trim()

        String model =  ""
        if(!price) { return }
        Integer result = db.insert("INSERT INTO `pickaboo_product` (`name`, `code`, `model`, `url`, `price`) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?, `model` = ?, `url` = ?, `price` = ?, `updated` = now()", [name, code, model, productUrl, price, name, model, productUrl, price])
        if(result) {
            println("Product: $name, Price: $price")
        } else {
            println("Product save failed: $code")
        }
    }

    static void crawlCategory(String categoryURL) {
        println("Crawling Category: ${categoryURL}")
        List<String> productURLs = []
        Document doc = SRHttpConnection.connect(categoryURL).get();
        while (doc) {
            productURLs.addAll(getAllProductURLs(doc))
            Element nextPage = doc.select(".category-products .nav-top-menu .pager .pages li a.next")[0]
            String nextPageURL = nextPage ? nextPage.attr("href") : null
            doc = nextPageURL ? SRHttpConnection.connect(nextPageURL).get() : null
        }
        productURLs.each {
            try {
                crawlProduct(it)
            } catch (Exception ex) {
                println("Product URL: "  + it + "\nMessage: "  + ex.message + "\n----------------------------------------------")
            }
        }
    }



    public static void CrawlCategories() {
        List categoryURLs = [
                "https://www.pickaboo.com/computer-pc/computer-accessories.html",
                "https://www.pickaboo.com/computer-pc/desktop-computer.html",
                "https://www.pickaboo.com/computer-pc/laptop-notebook.html",
                "https://www.pickaboo.com/computer-pc/apple-mac.html",
                "https://www.pickaboo.com/computer-pc/apple-accessories.html",
        ]
        ExecutorService executor = Executors.newFixedThreadPool(3);
        MyMonitorThread monitor = new MyMonitorThread(executor, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        categoryURLs.each { url ->
            executor.execute({
                try {
                    crawlCategory(url)
                } catch (Exception ex) {
                    println("Category URL: "  + url + "\nMessage: "  + ex.message + "\n----------------------------------------------")
                }
            })
        }
        executor.shutdown()
    }

    static void crawler() {

    }

    public static void main(String[] args) {
        CrawlCategories()
    }
}

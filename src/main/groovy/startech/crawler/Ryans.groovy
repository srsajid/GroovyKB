package startech.crawler

import org.jsoup.helper.SRHttpConnection;
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.jsoup.nodes.Element
import thread.pool.MyMonitorThread
import util.DB

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


public class Ryans {
    static db = new DB("price_compare");
    static ConcurrentHashMap<String, Integer> failedCount = new ConcurrentHashMap<String, Integer>()

    static List getAllProductURLs(Document categoryDoc) {
        List<String> productURLs = []
        Elements products = categoryDoc.select(".products-grid > li");
        for (Element product : products) {
            Element name = product.select(".product-name a")[0]
            if (name) {
                productURLs.add(name.attr("href"))
            }
        }
        return productURLs
    }

    static crawlProduct(String productUrl) {
        Document productDoc = SRHttpConnection.connect(productUrl).get();
        Elements specs = productDoc.select("#product-attribute-specs-table tr");
        String name = productDoc.select("h2[itemprop=name]")[0]?.text()?.trim()
        String code = productDoc.select(".productsku span")[0]?.text()?.trim()
        String regularPrice = productDoc.select("[itemprop=offers] .old-price .price-label")[0]?.text()?.trim()
        String price = productDoc.select("[itemprop=price]")[0]?.text()?.trim()
        if(price) {
            price = price.replaceAll("[A-Za-z,]", "").trim()
        }
        if(regularPrice) {
            regularPrice = regularPrice.replaceAll("[A-Za-z,]", "").trim()
        } else {
            regularPrice = "0"
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
        Integer result = db.insert("INSERT INTO `ryans_product` (`name`, `code`, `model`, `url`, `price`, `regular_price`) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?, `model` = ?, `url` = ?, `price` = ?, `regular_price` = ?, `updated` = now()", [name, code, model, productUrl, price, regularPrice, name, model, productUrl, price, regularPrice])
        if (result) {
            println("Product save succes: $code")
        } else {
            println("Product save failed: $code")
        }
    }

    static void crawlCategory(String categoryURL) {
        List<String> productURLs = []
        Document doc = SRHttpConnection.connect(categoryURL).get();
        while (doc) {
            productURLs.addAll(getAllProductURLs(doc))
            Element nextPage = doc.select(".pages .next.i-next")[0]
            String nextPageURL = nextPage ? nextPage.attr("href") : null
            doc = nextPageURL ? SRHttpConnection.connect(nextPageURL).get() : null
        }
        productURLs.each {
            try {
                crawlProduct(it)
            } catch (Exception ex) {
                println("Product URL: " + it + "\nMessage: " + ex.message + "\n----------------------------------------------")
            }
        }
    }

    static void crawler() {
        Document doc = SRHttpConnection.connect("https://ryanscomputers.com/").get();
        Elements menus = doc.select("ul.sm_megamenu_menu > li.other-toggle")
        menus.remove(0)
        List<String> categoryURLs = []
        menus.each {
            it.select("a").each {
                String url = it.attr("href").trim()
                if (url.startsWith("http")) {
                    categoryURLs.add(url)
                }
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(100);
        MyMonitorThread monitor = new MyMonitorThread(executor, 15);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        categoryURLs.each { url ->
            executor.execute({
                try {
                    crawlCategory(url)
                } catch (Exception ex) {
                    println("Category URL: " + url + "\nMessage: " + ex.message + "\n----------------------------------------------")
                }
            })
        }
        executor.shutdown()
    }

    public static void CrawlCategories() {
        List categoryURLs = [
                "https://ryanscomputers.com/monitor.html",
                "https://ryanscomputers.com/components/processor.html",
                "https://ryanscomputers.com/components/mainboard.html",
                "https://ryanscomputers.com/components/graphics-card.html",
                "https://ryanscomputers.com/components/mainboard.html",
                "https://ryanscomputers.com/components/desktop-ram.html",

        ]
        ExecutorService executor = Executors.newFixedThreadPool(5);
        MyMonitorThread monitor = new MyMonitorThread(executor, 20);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        categoryURLs.each { url ->
            executor.execute({
                try {
                    crawlCategory(url)
                    println("Im done")
                } catch (Exception ex) {
                    println("Category URL: " + url + "\nMessage: " + ex.message + "\n----------------------------------------------")
                }
            })
        }
        executor.shutdown()
    }

    public static void main(String[] args) {
        crawler()
        crawler()
        crawler()
        crawler()
    }
}

package startech.crawler

import org.jsoup.helper.SRHttpConnection
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import thread.pool.MyMonitorThread
import util.DB

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Techland {
    static db = new DB("price_compare");
    static Map crawlCache = [:]


    static crawlProduct(String productUrl) {
        if(crawlCache.containsKey(productUrl)) return
        Document productDoc = SRHttpConnection.connect(productUrl).get();
        String name = productDoc.select("#title-page")[0]?.text()?.trim()
        String code = productDoc.select("#product [name=product_id]")[0]?.val()?.trim()
        String price = productDoc.select("#quickview_product [itemprop=price]")[0]?.text()?.trim()
        if(price) {
            price = price.replaceAll(/[^\.0-9]/, "")
        }
        String model = ""
        Integer result = db.insert("INSERT INTO `techland_product` (`name`, `code`, `model`, `url`, `price`) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?, `model` = ?, `url` = ?, `price` = ?, `updated` = now()", [name, code, model, productUrl, price, name, model, productUrl, price])
        if(result) {
            println("Success - Code: $code Name: ${name} - Price: ${price}")
        } else {
            println("Failed - Code: $code Name: $name")
        }
        crawlCache[productUrl] = true
    }

    static List getAllProductURLs(Document categoryDoc) {
        List<String> productURLs = []
        Elements products = categoryDoc.select("#content .product-grid .product");
        for (Element product : products) {
            Element name = product.select(".name a")[0]
            if(name) {
                productURLs.add(name.attr("href"))
            }
        }
        return productURLs
    }

    static void crawlCategory(String categoryURL) {
        if(crawlCache.containsKey(categoryURL)) return
        List<String> productURLs = []
        Document doc = SRHttpConnection.connect(categoryURL).get();
        while (doc) {
            productURLs.addAll(getAllProductURLs(doc))
            Element nextPage = doc.select(".pagination .active").next()[0]
            String nextPageURL = nextPage ? nextPage.select("a").attr("href") : null
            doc = nextPageURL ? SRHttpConnection.connect(nextPageURL).get() : null
        }
        productURLs.each {
            try {
                crawlProduct(it)
            } catch (Exception ex) {
                println("Product URL: "  + it + "\nMessage: "  + ex.message + "\n----------------------------------------------")
            }
        }
        crawlCache[categoryURL] = true
    }

    static void crawler() {
        Document doc = SRHttpConnection.connect("https://www.techlandbd.com/").get();
        Elements menus = doc.select("header .megamenu > li")
        List<String> categoryURLs = []
        menus.each {
            it.select("a").each {
                String url = it.attr("href").trim()
                if (url.startsWith("http")) {
                    categoryURLs.add( url)
                }
            }
        }

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

    static void crawlPcComponents() {
        Document doc = SRHttpConnection.connect("https://www.techlandbd.com/").get();
        Elements menus = doc.select("header .megamenu > li:nth-child(3)")
        List<String> categoryURLs = []
        menus.each {
            it.select("a").each {
                String url = it.attr("href").trim()
                if (url.startsWith("http")) {
                    categoryURLs.add( url)
                }
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(5);
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

    public static void CrawlCategories() {
        List categoryURLs = [
        ]
        ExecutorService executor = Executors.newFixedThreadPool(6);
        MyMonitorThread monitor = new MyMonitorThread(executor, 5);
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

    public static void main(String[] args) {
        crawler()
    }
}

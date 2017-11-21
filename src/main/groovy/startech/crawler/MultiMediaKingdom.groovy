package startech.crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import thread.pool.MyMonitorThread
import util.DB

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

public class MultiMediaKingdom {
    static db = new DB("price_comapre");
    static ConcurrentHashMap<String, Integer> failedCount = new ConcurrentHashMap<String, Integer> ()

    static List getAllProductURLs(Document categoryDoc) {
        List<String> productURLs = []
        Elements products = categoryDoc.select(".product_item_wrapper");
        for (Element product : products) {
            Element name = product.select(".product-title a")[0]
            if(name) {
                productURLs.add(name.attr("href"))
            }
        }
        return productURLs
    }

    static crawlProduct(String productUrl) {
        Document productDoc = Jsoup.connect(productUrl).get();
        String name = productDoc.select(".product-title")[0]?.text()?.trim()
        String code = productDoc.select("[itemscope].product").attr("id")?.trim()
        String price = productDoc.select(".price .amount")[0]?.text()?.trim()
        if(price) {
            price = price.replace('৳', "").replaceAll("[A-Za-z,]", "").trim()
        }
        String model = productDoc.select(".wd_product_sku")[0]?.text()?.trim() ?: ""
        model = model.replace("Sku: ", "")
        Integer result = db.insert("INSERT INTO `mk_product` (`name`, `code`, `model`, `url`, `price`) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?, `model` = ?, `url` = ?, `price` = ?, `updated` = now()", [name, code, model, productUrl, price, name, model, productUrl, price])
//        if(result) {
//            println("Product save succes: $code")
//        } else {
//            println("Product save failed: $code")
//        }
    }

    static void crawlCategory(String categoryURL) {
        println("Crawling Category: ${categoryURL}")
        List<String> productURLs = []
        Document doc = Jsoup.connect(categoryURL).get();
        while (doc) {
            productURLs.addAll(getAllProductURLs(doc))
            Element nextPage = doc.select(".next.page-numbers")[0]
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



    public static void CrawlCategories() {
        List categoryURLs = [
                "http://www.startech.com.bd/accessories/webcam/logitech-webcam",
                "https://ryanscomputers.com/accessories/gaming.html"
        ]
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

    static void crawler() {
        Document doc = Jsoup.connect("http://multimediakingdom.com.bd/").get();
        Elements menus = doc.select("#menu-main li")
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

        menus = doc.select("ul.hover_mode li")
        menus.each {
            it.select("a").each {
                String url = it.attr("href").trim()
                if (url.startsWith("http")) {
                    categoryURLs.add( url)
                }
            }
        }
        ExecutorService executor = Executors.newFixedThreadPool(15);
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

    public static void main(String[] args) {
        crawler()
    }
}
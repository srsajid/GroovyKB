package startech.crawler

import groovy.json.JsonSlurper
import jdk.nashorn.internal.parser.JSONParser
import org.jsoup.helper.SRHttpConnection
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import thread.pool.MyMonitorThread
import util.DB

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

public class CustomMacKingdom {
    static HOST = "https://www.custommacbd.com"

    static db = new DB("price_compare");
    static ConcurrentHashMap<String, Integer> failedCount = new ConcurrentHashMap<String, Integer> ()

    static List getAllProductURLs(Document categoryDoc) {
        List<String> productURLs = []
        Elements products = categoryDoc.select(".grid-item .product-grid-item");
        for (Element product : products) {
            String url = product.attr("href")
            if(url && !url.startsWith("http")) {
                url = HOST + url
            }
            productURLs.add(url)
        }
        return productURLs
    }

    static crawlProduct(String productUrl) {
        Document productDoc = SRHttpConnection.connect(productUrl).get();
        String productJson = productDoc.select("#ProductJson-product-template")[0]?.html()?.trim()
        Map productData = new JsonSlurper().parseText(productJson)
        String name = productData.title
        String code = productData.id
        String model = ""
        String price = productData.price
        if(!price) { return }
        Integer result = db.insert("INSERT INTO `custom_mac_product` (`name`, `code`, `model`, `url`, `price`) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?, `model` = ?, `url` = ?, `price` = ?, `updated` = now()", [name, code, model, productUrl, price, name, model, productUrl, price])
        if(result) {
            println("Product save succes: $code")
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
            Element nextPage = doc.select(".pagination-custom > li:last-child")[0]
            String nextPageURL = nextPage ? nextPage.select("a").attr("href") : null
            doc = nextPageURL ? SRHttpConnection.connect(HOST + nextPageURL).get() : null
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
        Document doc = SRHttpConnection.connect(HOST).get();
        Elements menus = doc.select("#accessibleNav li")
        menus.remove(0)
        List<String> categoryURLs = []
        menus.each {
            it.select("a").each {
                String url = it.attr("href").trim()
                if(!url.startsWith("http")) {
                    url = HOST + url
                }
                categoryURLs.add(url)
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(50);
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

package startech.crawler

import http.HttpUtil
import org.jsoup.helper.SRHttpConnection
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import thread.pool.MyMonitorThread
import util.DB

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Binary {
    static db = new DB("price_compare");
    static HOST = "http://www.binarylogic.com.bd/";

    static crawlProduct(Element product) {
        String name = product.select(".item-title a")[0]?.text()?.trim()
        String url = product.select(".item-title a").attr("href")

        try {
            if (!url.startsWith("http")) {
                url = HOST + url
            }
            String code = product.select(".add-to-cart a").attr("data-product_id")
            String regularPrice = product.select(".regular-price del .amount")[0]?.text()?.trim()
            if(regularPrice) {
                regularPrice = regularPrice.replaceAll(/[^\.0-9]/, "")
            }

            String price = product.select(".regular-price ins .amount")[0]?.text()?.trim()
            if(!price) {
                price = product.select(".regular-price .amount")[0]?.text()?.trim()
            }

            if(price) {
                price = price.replaceAll(/[^\.0-9]/, "")
            }
            String model = ""

            Integer result = db.insert("INSERT INTO `binary_product` (`name`, `code`, `model`, `url`, `price`) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?, `model` = ?, `url` = ?, `price` = ?, `updated` = now()", [name, code, model, url, price, name, model, url, price])

            if(result) {
                println("Success - Code: $code Name: ${name} - Price: ${price}")
            } else {
                println("Failed - Code: $code Name: $name")
            }
        } catch (Exception ex) {
            println(ex.message + "Url: $url")
        }

    }

    static void crawlProducts(Document categoryDoc) {
        Elements products = categoryDoc.select(".products-grid .product-item");
        for (Element product : products) {
            crawlProduct(product)
        }
    }

    static void crawlCategory(String categoryURL) {
        Document doc = SRHttpConnection.connect(categoryURL).get();
        while (doc) {
            crawlProducts(doc)
            Element nextPage = doc.select(".next.page-numbers")[0]
            String nextPageURL = nextPage ? nextPage.attr("href") : null
            doc = nextPageURL ? SRHttpConnection.connect(nextPageURL).get() : null
        }
    }

    static void crawler() {
        Document doc = SRHttpConnection.connect(HOST).get();
        Elements menus = doc.select("#menu-main-menu-1 a:not(.btn-main)")
        List<String> categoryURLs = []
        menus.each {
            String url = it.attr("href").trim()
            if (!url.startsWith("http")) {
                url = HOST + url
            }
            categoryURLs.add( url)
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
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

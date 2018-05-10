package startech.crawler

import org.jsoup.Jsoup
import org.jsoup.helper.SRHttpConnection
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import thread.pool.MyMonitorThread
import util.DB

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Village {
    static db = new DB("price_compare");

    static crawlProduct(String productUrl) {
        Document productDoc = SRHttpConnection.connect(productUrl).get();
        Elements specs = productDoc.select("#specification tr");
        String name = productDoc.select(".titles .headinglefttitle")[0]?.text()?.trim()
        String code = productDoc.select("#review [name=product_id]")[0]?.val()?.trim()
        String price = productDoc.select(".productbox .productpageprice")[0]?.text()?.trim()
        if(price) {
            price = price.replaceAll(/[^\.0-9]/, "")
        }
        String model = ""
        Iterator<Element> iter = specs.iterator()
        while (iter.hasNext()) {
            Element spec = iter.next();
            String label = spec.select("th")[0]?.text()?.trim()
            String value = spec.select("td")[0]?.text()?.trim()
            if(label == "Model") {
                model = value
                break
            }
        }
        println("Name: ${name}\nPrice: ${price}\nURL:  ${productUrl}\nModel:${model}\n\n")
        Integer result = db.insert("INSERT INTO `village_product` (`name`, `code`, `model`, `url`, `price`) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?, `model` = ?, `url` = ?, `price` = ?, `updated` = now()", [name, code, model, productUrl, price, name, model, productUrl, price])
//        if(result) {
//            println("Product save succes: $code")
//        } else {
//            println("Product save failed: $code")
//        }
    }

    static List getAllProductURLs(Document categoryDoc) {
        List<String> productURLs = []
        Elements products = categoryDoc.select("#productgrid > ul > li");
        for (Element product : products) {
            Element name = product.select(".pro-name a")[0]
            if(name) {
                productURLs.add(name.attr("href"))
            }
        }
        return productURLs
    }

    static void crawlCategory(String categoryURL) {
        List<String> productURLs = []
        Document doc = SRHttpConnection.connect(categoryURL).get();
        while (doc) {
            productURLs.addAll(getAllProductURLs(doc))
            Element nextPage = doc.select(".productlistpage .pagination .next a")[0]
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

    static void crawler() {
        Document doc = SRHttpConnection.connect("http://village-bd.com/").get();
        Elements menus = doc.select("#mainmenu ul.navul > li.dropdown:not(.home,.service,.weekly-hot)")
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

        ExecutorService executor = Executors.newFixedThreadPool(20);
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
                "http://village-bd.com/category/archive/laptop-hp",
                "http://village-bd.com/category/archive/laptop-dell",
                "http://village-bd.com/category/archive/laptop-asus",
                "http://village-bd.com/category/archive/laptop-lenovo",
                "http://village-bd.com/category/archive/laptop-acer",
                "http://village-bd.com/category/archive/i-life",
                "http://village-bd.com/category/archive/laptop-fujitsu"
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
//        crawlCategory("http://village-bd.com/category/archive/laptops-notebooks") //387
//        CrawlCategories()
        crawler()
    }
}

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
    static Map crawlCache = [:]
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

    static Map get(String code) {
        List results = db.getResult("select * from ryans_product where `code` = '${code}'")
        return  results ? results.first() : null
    }

    static Integer add(name, code, model, type, productUrl, price, regularPrice) {
       return db.insert("INSERT INTO `ryans_product` (`name`, `code`, `model`, `type`, `url`, `price`, `regular_price`) VALUES (?, ?, ?, ?, ?, ?, ?)", [name, code, model, type, productUrl, price, regularPrice])
    }

    static Integer update(name, code, model, type, productUrl, price, regularPrice) {
        return db.insert("UPDATE `ryans_product` SET `name` = ?, `model` = ?, `type` = ?, `url` = ?, `price` = ?, `regular_price` = ?, `updated` = now() WHERE code = ?", [name, model, type, productUrl, price, regularPrice, code])
    }

    static String getType(Document productDoc) {
        Elements breadcrumbItems = productDoc.select(".breadcrumbs ul li:not(.home, .home-fix, .current)");
        String type = breadcrumbItems.collect({
            it.text().toString()
        }).join(" > ")
        return type;
    }

    static crawlProduct(String productUrl) {
        if(crawlCache.containsKey(productUrl))  {
            return
        }
        Document productDoc = SRHttpConnection.connect(productUrl).get();
        Elements specs = productDoc.select("#product-attribute-specs-table tr");
        String name = productDoc.select("#product_addtocart_form .product-name")[0]?.text()?.trim()
        String code = productDoc.select(".productsku span")[0]?.text()?.trim()
        String regularPrice = productDoc.select("#product_addtocart_form .old-price .price-label")[0]?.text()?.trim()
        String price = productDoc.select("#product_addtocart_form .special-price .price-label .price")[0]?.text()?.trim()
        String type = getType(productDoc)
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
        Map ryansProduct = get(code)
        Integer result
        if(ryansProduct) {
            type = type.size() > ryansProduct.type.size() ? type : ryansProduct.type
            result = update(name, code, model, type, productUrl, price, regularPrice)
        } else  {
            result = add(name, code, model, type, productUrl, price, regularPrice)
        }

        if (result) {
            println("Product save succes: $code")
        } else {
            println("Product save failed: $code")
        }

        crawlCache[productUrl] = true
    }

    static void crawlCategory(String categoryURL) {
        if(crawlCache.containsKey(categoryURL)) {
            return
        }
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
        crawlCache[categoryURL] = true
    }

    static void crawler() {
        Document doc = SRHttpConnection.connect("https://ryanscomputers.com/").get();
        Elements menus = doc.select("ul.sm_megamenu_menu > li.other-toggle")
        List<String> categoryURLs = []
        menus.each {
            it.select("a").each {
                String url = it.attr("href").trim()
                if (url.startsWith("http")) {
                    categoryURLs.add(url)
                }
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(40);
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
                "https://ryanscomputers.com/laptop-notebook.html",
                "https://ryanscomputers.com/monitor.html",
                "https://ryanscomputers.com/components/processor.html",
                "https://ryanscomputers.com/components/mainboard.html",
                "https://ryanscomputers.com/components/graphics-card.html",
                "https://ryanscomputers.com/components/mainboard.html",
                "https://ryanscomputers.com/components/desktop-ram.html",

        ]
        ExecutorService executor = Executors.newFixedThreadPool(2);
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
    }
}

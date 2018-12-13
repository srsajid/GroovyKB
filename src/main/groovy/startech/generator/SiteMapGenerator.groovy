package startech.generator

import groovy.xml.MarkupBuilder
import startech.util.URL
import util.DB;


public class SiteMapGenerator {
    private static final String DB_PREFIX = 'sr_'
    DB db
    URL url
    String host = "www.startech.com.bd"

    SiteMapGenerator(DB db, String host = null) {
        this.db = db ?: new DB('startech')
        if(host) this.host = host
        url = new URL(this.db, this.host)
    }


    List getCategories(Integer parent_id) {
       return db.getResult("SELECT * FROM " + DB_PREFIX + "category c LEFT JOIN " + DB_PREFIX + "category_description cd ON (c.category_id = cd.category_id) LEFT JOIN " + DB_PREFIX + "category_to_store c2s ON (c.category_id = c2s.category_id) WHERE c.parent_id = '" + parent_id + "' AND cd.language_id = '1' AND c2s.store_id = '0'  AND c.status = '1' ORDER BY c.sort_order, LCASE(cd.name)")
    }

    List getCategoryManufacturer(Integer category_id) {
        return db.getResult("select * from sr_category_manufacturer cm left join sr_category_manufacturer_description cmd on cm.category_manufacturer_id = cmd.category_manufacturer_id where cm.category_id = $category_id")
    }

    List<String> getAllCategoryUrl() {
        List<String> urls = []
        List categories = getCategories(0)
        for (Map category : categories) {
            urls.add(this.url.rewrite(route: 'product/category', path: category['category_id']))

            List children_level_1 = this.getCategories(Integer.parseInt(category['category_id']));
            for (Map child : children_level_1) {
                urls.add(this.url.rewrite(route: 'product/category', path: category['category_id'] + '_' + child['category_id']))
                List children_level_2 = this.getCategories(Integer.parseInt(child['category_id']));

                for (Map child_2 : children_level_2) {
                    urls.add(this.url.rewrite(route: 'product/category', path: category['category_id'] + '_' + child['category_id'] + '_' + child_2['category_id']))
                }
                if(children_level_2) continue
                children_level_2 = getCategoryManufacturer(Integer.parseInt(child['category_id']))
                for (Map child_2 : children_level_2) {
                    urls.add(this.url.rewrite(route: 'product/manufacturer/info', path: category['category_id'] + '_' + child['category_id'], manufacturer_id: child_2['manufacturer_id']))
                }
            }
            if(children_level_1) continue
            children_level_1 = getCategoryManufacturer(Integer.parseInt(category['category_id']))
            for (Map child : children_level_1) {
                urls.add(this.url.rewrite(route: 'product/manufacturer/info', path: category['category_id'], manufacturer_id: child['manufacturer_id']))
            }
        }
        return urls;
    }

    List<String> getProductURLs() {
        List<String> urls = []
        List<Map> products = db.getResult('select * from sr_product p where p.`status` = 1')
        products.each {
            urls.add(this.url.rewrite(route: 'product/product', product_id: it['product_id']))
        }
        return urls
    }

    def generate() {
        def writer = new FileWriter("C:\\MyDrive\\${db.database}\\sitemap.xml")
        def xml = new MarkupBuilder(writer)
        xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
        xml.urlset(xmlns: "http://www.sitemaps.org/schemas/sitemap/0.9") {
            url {
                loc("https://${this.host}/")
                priority("1")
                changefreq('weekly')
            }
            getAllCategoryUrl().each { u ->
                url {
                    loc(u)
                    priority(".5")
                    changefreq('weekly')
                }
            }
            getProductURLs().each { u ->
                url {
                    loc(u)
                    priority(".5")
                    changefreq('weekly')
                }
            }
        }
        writer.close()
    }

    public static void main(String[] args) {
        new SiteMapGenerator().generate()
    }
}

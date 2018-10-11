package startech.services

import org.apache.commons.lang.StringEscapeUtils
import org.springframework.web.util.UriUtils
import startech.util.URL
import util.DB

class ProductService {
    DB db
    URL url
    Map stockStatusIndex

    ProductService(DB db)  {
        this.db = db ?: new DB("startech")
        this.url = new URL(this.db)
        stockStatusIndex = [:]
        this.db.getResult("select * from sr_stock_status").each {
            stockStatusIndex[it.stock_status_id] = it.name
        }
    }

    List getProducts(Map params) {
        String sql = "select *, (CASE WHEN p.quantity > 0 THEN 1 ELSE 0 END) qty from sr_product p left join sr_product_description pd on p.product_id = pd.product_id where p.`status` = 1"
        if(params.in_stock) {
            sql += " and p.quantity > 0"
        }
        if(params.category_id) {
            sql += " and p.product_id in (select ptc.product_id from sr_product_to_category ptc where ptc.category_id = ${params.category_id})"
        }
        if(params.manufacturer_id) {
            sql += " and p.manufacturer_id = '${params.manufacturer_id}'"
        }
        sql += "order by p.manufacturer_id asc, qty desc, p.price asc"
        List<Map> results = [];
        db.getResult(sql).each {Map product ->
            product.name = StringEscapeUtils.unescapeHtml(product.name)
            product.url = url.rewrite(route: 'product/product', product_id: product['product_id']);
            product.image = "https://www.startech.com.bd/image/${UriUtils.encodePath(product.image, "utf-8")}"
            Integer quantity = Integer.parseInt(product.quantity);
            if(product.manufacturer_id) {
                List manufacturer = db.getResult("select m.manufacturer_id, m.name, md.meta_title, md.meta_description from sr_manufacturer_description md left join sr_manufacturer m on md.manufacturer_id = m.manufacturer_id where m.manufacturer_id = ${product.manufacturer_id}")
                if(manufacturer) product.manufacturer  = manufacturer.first().name
            }
            product.stock_status = quantity > 0 ? "In Stock" : stockStatusIndex[product.stock_status_id]
            product.short_description = product.short_description.trim()
            results.add(product)
        }
        return results
    }
}

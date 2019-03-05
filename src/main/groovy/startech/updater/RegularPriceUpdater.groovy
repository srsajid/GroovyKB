package startech.updater

import util.DB

class RegularPriceUpdater {

    static List getProducts(Map params) {
        DB db = new DB("price_compare")
        String sql = "select * from sr_product p left join sr_product_description pd on p.product_id = pd.product_id where p.`status` = 1"
        if(params.categoryId) {
            sql += " and p.product_id in (select ptc.product_id from sr_product_to_category ptc where ptc.category_id = ${params.categoryId})"
        }
        if(params.emi) {
            sql += " and emi = 1"
        }

        return db.getResult(sql)
    }


    static Double getPrice(Double price) {
        return price + 0.05 * price
    }

    public static void main(String[] args) {
        getProducts([categoryId: 723]).each {Map product ->
            if(product.manufacturer_id != "12") {
                String response = OCPriceSheetUpdater.updatePrice(product.product_id, "", Integer.parseInt(product.sort_order) + 100, "", "")
                println(response)
            }

        }
    }
}

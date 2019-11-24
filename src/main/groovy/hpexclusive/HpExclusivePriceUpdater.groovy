package hpexclusive

import org.apache.commons.lang.StringEscapeUtils
import org.apache.poi.hssf.usermodel.HSSFCell
import startech.updater.LocalDatabaseUpdater
import startech.updater.OCPriceSheetUpdater
import util.DB
class Product {
    Long id
    Integer quantity
    Integer sortOrder
    Integer stockStatusId
    Double price

    public Product(Long id, Integer quantity, Integer sortOrder,Integer stockStatusId, Double price) {
        this.id = id
        this.quantity = quantity
        this.sortOrder = sortOrder
        this.stockStatusId = stockStatusId
        this.price = price
    }
}

class PriceUpdater {

    static PRODUCT_STOCK_STATUS = [
            IN_STOCK: 7,
            DAYS_2_3: 6,
            CALL_FOR_PRICE: 10,
            OUT_OF_STOCK: 5,
            PRE_ORDER: 8,
            UPCOMING: 9,
    ]

    public static void main(String[] args) {
        DB starDB = new DB("startech")
        println("Updating Star Tech Database .....")
//        new LocalDatabaseUpdater().updateDatabase(starDB, LocalDatabaseUpdater.advance, "")

        DB db  = new DB("hpexclusive")
        println("Updating HP Exclusive Database .....")
//        new LocalDatabaseUpdater().updateDatabase(db, LocalDatabaseUpdater.advance, "www.hpexclusive.com.bd")

        Scanner scanner = new Scanner(System.in)
        print("Category Id:")
        Long categoryId = scanner.nextInt()

        List<Product> productList = []
        db.getResult("select * from sr_product p left join sr_product_description pd on p.product_id = pd.product_id where p.`status` = 1 and p.product_id in (select product_id from sr_product_to_category ptc where ptc.category_id = ${categoryId}) ").each {
            Integer sortOrder = null
            Double price = null
            def starProduct
            if (it.sku) {
                List results = starDB.getResult("select * from sr_product p left join sr_product_description pd on p.product_id = pd.product_id where p.product_id = ${it.sku}")
                if(results.size()) {
                    starProduct = results.first()
                }
            }
            if(starProduct) {
                sortOrder =  Integer.parseInt( starProduct.sort_order)
                price = Double.parseDouble( starProduct.price)
                println(StringEscapeUtils.unescapeHtml(it.name)  + " ----- " + StringEscapeUtils.unescapeHtml(starProduct.name))
            } else {
                println("SKU not Found: ${it.name}")
            }

            Product product = new Product(
                   Long.parseLong(it.product_id),
                    Integer.parseInt(it.quantity),
                   (sortOrder ?: Integer.parseInt(it.sort_order)),
                    Integer.parseInt(it.stock_status_id),
                   (price ?: Double.parseDouble(it.price)),
            )
            productList.add(product)
        }

        print("Type: ")
        String type = scanner.next()
        productList.sort {Product ob1, Product ob2 ->
            if((ob1.quantity && ob2.quantity) || (ob1.stockStatusId == ob2.stockStatusId)) {
                if(type == "p") {
                    return ob1.price <=> ob2.price
                } else {
                    return ob1.sortOrder <=> ob2.sortOrder
                }
            }

            if(ob1.quantity) {
                return -1
            }

            if(ob2.quantity) {
                return 1
            }

            if(ob1.stockStatusId == PRODUCT_STOCK_STATUS.OUT_OF_STOCK) {
                return 1
            }

            if(ob2.stockStatusId == PRODUCT_STOCK_STATUS.OUT_OF_STOCK) {
                return -1
            }

            if(ob1.stockStatusId == PRODUCT_STOCK_STATUS.PRE_ORDER) {
                return 1
            }
            return 0
        }

        Integer start = 1
        print("Operator Email: ")
        String operatorEmail = scanner.next()
        print("Operator Pass: ")
        String operatorPass = scanner.next()
        String encoding = Base64.getEncoder().encodeToString("$operatorEmail:$operatorPass".getBytes());
        productList.eachWithIndex { Product product, int i ->
            Integer newSortOrder = i + start;
            OCPriceSheetUpdater.updatePrice(product.id, '', newSortOrder, product.price, '', "https://www.hpexclusive.com.bd/", encoding)
        }

    }
}


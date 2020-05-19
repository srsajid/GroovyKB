package hpexclusive

import startech.services.ProductService
import startech.updater.LocalDatabaseUpdater
import startech.updater.OCPriceSheetUpdater
import util.DB

/**
 * Created by user on 1/14/2020.
 */
class MissProduct {
    public static void main(String[] args) {
        DB starDB = new DB("startech")
        println("Updating Star Tech Database .....")
        new LocalDatabaseUpdater().updateDatabase(starDB, LocalDatabaseUpdater.advance, "")

        DB db  = new DB("hpexclusive")
        println("Updating HP Exclusive Database .....")
        new LocalDatabaseUpdater().updateDatabase(db, LocalDatabaseUpdater.advance, "www.hpexclusive.com.bd")

        ProductService productService = new ProductService(starDB)
        ProductService hpProductService = new ProductService(db)

        productService.getProducts([manufacturer_id: 22]).each {
            List products = hpProductService.getProducts([sku: it.product_id])
            if(!products) {
                println(it.name)
            }
        }
    }
}

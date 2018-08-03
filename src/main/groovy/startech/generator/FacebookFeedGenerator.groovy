package startech.generator

import org.supercsv.cellprocessor.constraint.NotNull
import org.supercsv.cellprocessor.ift.CellProcessor
import org.supercsv.io.CsvMapWriter
import org.supercsv.io.ICsvMapWriter
import org.supercsv.prefs.CsvPreference
import startech.services.CategoryService
import startech.services.ProductService
import util.DB

import java.text.NumberFormat

class FacebookFeedGenerator {
    private static CellProcessor[] getProcessors() {

        final CellProcessor[] processors =[
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
        ]

        return processors;
    }
    public static void main(String[] args) {
        Map cache = [:]
        DB db = new DB("startech")
        CategoryService categoryService = new CategoryService(db)
        ProductService productService = new ProductService(db)

        final String[] header = [
                "id", // 0
                "title", // 1
                "description", // 2
                "availability", // 3
                "condition", // 4
                "price", // 5
                "link", // 6
                "image_link", // 7
                "brand", // 8
                "product_type" // 9

        ];
        ICsvMapWriter mapWriter = new CsvMapWriter(new FileWriter("c:\\MyDrive\\facebook.csv"), CsvPreference.STANDARD_PREFERENCE);
        final CellProcessor[] processors = getProcessors();
        mapWriter.writeHeader(header); NumberFormat format = NumberFormat.getInstance(Locale.default);
        int count = 0
        categoryService.leafCategories.each {Map category ->
            List products = productService.getProducts([category_id: category.category_id])
            products.each {Map product ->
                if(cache.containsKey(product.product_id)) return
                if(!product.manufacturer) {
                    println(product.name)
                    count++
                    return
                }
                if (!product.stock_status) {
                    return
                }
                String stockStatus = product.stock_status.toLowerCase().trim();
                if(stockStatus != "in stock" && stockStatus != "out of stock") {
                    stockStatus == "preorder"
                }
                mapWriter.write([
                    id: product.product_id,
                    title: product.name,
                    description: product.short_description ?: product.meta_description,
                    availability: stockStatus,
                    condition: "new",
                    price:  String.format("%.2f BDT", Double.parseDouble(product.price)),
                    link: product.url,
                    image_link: product.image,
                    brand: product.manufacturer,
                    product_type: category.name
                ], header, processors);
                cache[product.product_id] = true
            }
        }
        mapWriter.close();
        println(count)
    }
}

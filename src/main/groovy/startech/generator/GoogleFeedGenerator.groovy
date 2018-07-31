package startech.generator

import org.apache.commons.lang.StringEscapeUtils
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.web.util.UriUtils
import startech.util.URL
import util.DB

import java.text.NumberFormat

class GoogleFeedGenerator {
    public static void main(String[] args) {
        DB db = new DB("startech");
        URL url = new URL(db);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("dynamic remarketing product fee");
        Integer i = 0
        HSSFRow row = sheet.createRow(i++);
        ["ID", "Item title", "Final URL", "Image URL", "Item category", "Price"].eachWithIndex { String entry, int j ->
            HSSFCell cell = row.createCell(j);
            cell.setCellValue(entry)
        }
        NumberFormat format = NumberFormat.getInstance(Locale.default);
        List<Map> rootCategories =  db.getResult("select DISTINCT c.category_id, cd.name from  sr_category c left join sr_category_description cd on c.category_id = cd.category_id where c.parent_id = 0")
        rootCategories.each { Map rootCategory ->
            List<Map> childCategories =  db.getResult("select DISTINCT c.category_id, cd.name from  sr_category c left join sr_category_description cd on c.category_id = cd.category_id where c.parent_id = ${rootCategory.category_id}")
            childCategories.each {
                List<Map> products =  db.getResult("select * from sr_product p left join sr_product_description pd on p.product_id = pd.product_id where p.`status` = 1 and p.product_id in (select ptc.product_id from sr_product_to_category ptc where ptc.category_id = ${it.category_id})")
                products.each { Map product ->
                    row = sheet.createRow(i++);
                    String productUrl = url.rewrite(route: 'product/product', product_id: product['product_id']);
                    String imageUrl = "https://www.startech.com.bd/image/${UriUtils.encodePath(product.image, "utf-8")}"

                    [product.product_id, StringEscapeUtils.unescapeHtml(product.name), productUrl, imageUrl, StringEscapeUtils.unescapeHtml(it.name), format.format(Double.parseDouble(product.price)) + " BDT"].eachWithIndex { String entry, int j ->
                        HSSFCell cell = row.createCell(j);
                        cell.setCellValue(entry)
                    }
                }

                FileOutputStream fileOut = new FileOutputStream("c:\\MyDrive\\all product sheet.xls");
                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }

        }
    }
}

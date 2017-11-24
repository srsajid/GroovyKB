package startech

import org.apache.commons.lang.StringEscapeUtils
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import util.DB


class OCPriceSheetGenerator {
    final private static int PARENT_CATEGORY = 102;

    public static void main(String[] args) {
        DB db = new DB("startech");
        Map stockStatusIndex = [:]
        db.getResult("select * from sr_stock_status").each {
            stockStatusIndex[it.stock_status_id] = it.name
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        List<Map> childCategories =  db.getResult("select DISTINCT c.category_id, cd.name from  sr_category c left join sr_category_description cd on c.category_id = cd.category_id where c.parent_id = $PARENT_CATEGORY")
        childCategories.each {
            HSSFSheet sheet = wb.createSheet(StringEscapeUtils.unescapeHtml(it.name));
            HSSFRow row = sheet.createRow(0);
            ["Product ID", "Name", "Model", "Price", "Status", "New Price", "New Status"].eachWithIndex { String entry, int j ->
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(entry)
            }
            List<Map> products =  db.getResult("select * from sr_product p left join sr_product_description pd on p.product_id = pd.product_id where p.`status` = 1 and p.product_id in (select ptc.product_id from sr_product_to_category ptc where ptc.category_id = ${it.category_id})")
            products.eachWithIndex { Map product, int i ->
                Integer quantity = Integer.parseInt(product.quantity);
                String status = quantity > 0 ? "In Stock" : stockStatusIndex[product.stock_status_id]
                row = sheet.createRow(i + 1);
                [product.product_id, product.name, product.model, product.price, status, "", ""].eachWithIndex { String entry, int j ->
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(entry)
                }
            }

            FileOutputStream fileOut = new FileOutputStream("c:\\MyDrive\\DSLR Camera.xls");
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }
    }

}

package startech.generator

import org.apache.commons.lang.StringEscapeUtils
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFFont
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import startech.services.ProductService
import util.DB


class VendorPriceSheetGenerator {
    final private static int PARENT_CATEGORY = 201;

    public static void main(String[] args) {
        DB db = new DB("startech");
        ProductService productService = new ProductService(db)
        HSSFWorkbook wb = new HSSFWorkbook();
        List<Map> childCategories =  db.getResult("select DISTINCT c.category_id, cd.name from  sr_category c left join sr_category_description cd on c.category_id = cd.category_id where c.parent_id = $PARENT_CATEGORY")
        childCategories.each {
            HSSFSheet sheet = wb.createSheet(StringEscapeUtils.unescapeHtml(it.name));
            HSSFRow row = sheet.createRow(0);
            HSSFCellStyle headStyle = wb.createCellStyle();
            headStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            HSSFFont font = wb.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setBold(true)
            headStyle.setFont(font)

            ["Product ID", "Name", "URL", "Status", "Price", "DP Price"].eachWithIndex { String entry, int j ->
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(entry)
                cell.setCellStyle(headStyle);
            }
            sheet.setColumnWidth(0, 2560);
            sheet.setColumnWidth(1, 18000);
            sheet.setColumnWidth(2, 10000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 5000);

            List<Map> products = productService.getProducts([category_id: it.category_id, in_stock: true])
            products.eachWithIndex { Map product, int i ->
                row = sheet.createRow(i + 1);
                [product.product_id, product.name, product.url, product.stock_status, product.price, ""].eachWithIndex { String entry, int j ->
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(entry)
                }
            }
        }
        FileOutputStream fileOut = new FileOutputStream("c:\\MyDrive\\laptop_dp_price.xls");
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

}

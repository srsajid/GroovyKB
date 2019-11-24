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
import startech.services.CategoryService
import startech.services.ProductService
import util.DB


class OCPriceSheetGenerator {
    final private static int PARENT_CATEGORY = 120;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in)
        print("Database: ")
        String database = scanner.nextLine()
        DB db = new DB(database);
        CategoryService categoryService = new CategoryService(db)
        ProductService productService = new ProductService(db)

        HSSFWorkbook wb = new HSSFWorkbook();
        List<Map> childCategories =  categoryService.getLeafCategories()
        if(childCategories.size() == 0) {
            Map category = categoryService.getCategory(PARENT_CATEGORY)
            childCategories.add([category_id: category.id, name: category.description.name])
        }
        HSSFCellStyle style = wb.createCellStyle()
        style.setWrapText(true)

        HSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFFont font = wb.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true)
        headStyle.setFont(font)

        childCategories.each {
            try {
                List nameParts = it.name.split(">").collect { it.trim() };
                if(nameParts.size() > 2) {
                    nameParts.remove(0)
                }
                String sheetname = StringEscapeUtils.unescapeHtml(nameParts.join("_"))
                HSSFSheet sheet = wb.createSheet(sheetname);
                HSSFRow row = sheet.createRow(0);
                ["Product ID", "Name", "SKU", "Quantity", "Price", "New Price", "Regular Price", "New Regular Price" ,"Status", "New Status", "Sort Order", "New Sort Order"].eachWithIndex { String entry, int j ->
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(entry)
                    cell.setCellStyle(headStyle);
                }
                sheet.setColumnWidth(0, 2560);
                sheet.setColumnWidth(1, 18000);
                sheet.setColumnWidth(2, 7000);
                sheet.setColumnWidth(3, 3500);
                sheet.setColumnWidth(4, 3500);
                sheet.setColumnWidth(5, 3500);
                sheet.setColumnWidth(6, 5000);
                sheet.setColumnWidth(7, 5000);
                sheet.setColumnWidth(8, 5000);
                sheet.setColumnWidth(9, 5000);
                sheet.setColumnWidth(10, 5000);
                sheet.setColumnWidth(11, 6000);
                List<Map> products = productService.getProducts([category_id: it.category_id, manufacturer_id: it.manufacturer_id])
                products.eachWithIndex { Map product, int i ->
                    row = sheet.createRow(i + 1);
                    [product.product_id, StringEscapeUtils.unescapeHtml(product.name), product.sku, product.quantity, product.special ?: product.price, "", product.regular_price, "", product.stock_status, "", product.sort_order, ""].eachWithIndex { String entry, int j ->
                        HSSFCell cell = row.createCell(j);
                        cell.setCellValue(entry)
                        cell.setCellStyle(style)
                    }
                }
            } catch (Exception ex) {
                println(ex.message)
            }
        }
        FileOutputStream fileOut = new FileOutputStream("c:\\MyDrive\\${database}_price_sheet.xls");
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

}

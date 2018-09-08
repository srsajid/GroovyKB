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
        DB db = new DB("startech");
        CategoryService categoryService = new CategoryService(db)
        ProductService productService = new ProductService(db)

        HSSFWorkbook wb = new HSSFWorkbook();
        List<Map> childCategories =  categoryService.getChildCategories(PARENT_CATEGORY)
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
            HSSFSheet sheet = wb.createSheet(StringEscapeUtils.unescapeHtml(it.name));
            HSSFRow row = sheet.createRow(0);
            ["Product ID", "Name", "Model", "Price", "New Price", "Regular Price", "New Regular Price" ,"Status", "New Status", "Sort Order", "New Sort Order"].eachWithIndex { String entry, int j ->
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(entry)
                cell.setCellStyle(headStyle);
            }
            sheet.setColumnWidth(0, 2560);
            sheet.setColumnWidth(1, 18000);
            sheet.setColumnWidth(2, 7000);
            sheet.setColumnWidth(3, 3500);
            sheet.setColumnWidth(5, 3500);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(7, 5000);
            sheet.setColumnWidth(8, 5000);
            sheet.setColumnWidth(9, 5000);
            sheet.setColumnWidth(10, 6000);
            List<Map> products = productService.getProducts([category_id: it.category_id])
            products.eachWithIndex { Map product, int i ->
                row = sheet.createRow(i + 1);
                [product.product_id, StringEscapeUtils.unescapeHtml(product.name), StringEscapeUtils.unescapeHtml(product.model), product.price, "", product.regular_price, "", product.stock_status, "", product.sort_order, ""].eachWithIndex { String entry, int j ->
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(entry)
                    cell.setCellStyle(style)
                }
            }
        }
        FileOutputStream fileOut = new FileOutputStream("c:\\MyDrive\\accessories-price-List.xls");
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

}

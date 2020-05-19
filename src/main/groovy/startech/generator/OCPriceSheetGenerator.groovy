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
      while (true) {
          Scanner scanner = new Scanner(System.in)
          print("Database: ")
          String database = "startech"

          print("Sheet Name: ")
//          String sheetname = scanner.nextLine()

          print("Category Id: ")
          String categoryId = scanner.nextLine()

          DB db = new DB(database);
          CategoryService categoryService = new CategoryService(db)
          ProductService productService = new ProductService(db)

          Map category = categoryService.getCategory(categoryId)
          String sheetname = category.name

          HSSFWorkbook wb = new HSSFWorkbook();
          List<Map> childCategories =  categoryService.getChildCategories(categoryId)
          childCategories.addAll(categoryService.getCategoryManufacturer(categoryId))
          if(childCategories.size() == 0) {
              childCategories.add([category_id: category.category_id, name: category.name])
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

          HSSFSheet sheet = wb.createSheet(sheetname);
          HSSFRow row = sheet.createRow(0);
          ["Product ID", "Name", "Web Price", "Cost Price", "Discount", "New Price"].eachWithIndex { String entry, int j ->
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

          int i = 1;
          childCategories.each {
              try {
                  List<Map> products = productService.getProducts([category_id: it.category_id, manufacturer_id: it.manufacturer_id])
                  products.each { Map product ->
                      row = sheet.createRow(i);
                      [product.product_id, StringEscapeUtils.unescapeHtml(product.name), product.special ?: product.price, "", "", ""].eachWithIndex { String entry, int j ->
                          HSSFCell cell = row.createCell(j);
                          cell.setCellValue(entry)
                          cell.setCellStyle(style)
                      }
                      i++
                  }

              } catch (Exception ex) {
                  println(ex.message)
              }
          }
          FileOutputStream fileOut = new FileOutputStream("c:\\MyDrive\\sheets\\${sheetname}.xls");
          wb.write(fileOut);
          fileOut.flush();
          fileOut.close();
      }
    }

}

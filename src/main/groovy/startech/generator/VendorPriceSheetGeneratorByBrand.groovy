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
import startech.services.ManufacturerService
import startech.services.ProductService
import util.DB


class VendorPriceSheetGeneratorByBrand {

    public static void main(String[] args) {
        DB db = new DB("startech");
        ProductService productService = new ProductService(db)
        ManufacturerService manufacturerService = new ManufacturerService(db)

        HSSFWorkbook wb = new HSSFWorkbook();
        args.each {
            Map brandInfo = manufacturerService.getManufacturerByName(it)
            HSSFSheet sheet = wb.createSheet(StringEscapeUtils.unescapeHtml(brandInfo.name));
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


            List<Map> products = productService.getProducts([manufacturer_id: brandInfo.manufacturer_id, in_stock: true])
            products.eachWithIndex { Map product, int i ->
                row = sheet.createRow(i + 1);
                [product.product_id, product.name, product.url, product.stock_status, product.price, ""].eachWithIndex { String entry, int j ->
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(entry)
                }
            }

        }
        FileOutputStream fileOut = new FileOutputStream("c:\\MyDrive\\db_price_brand.xls");
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

}

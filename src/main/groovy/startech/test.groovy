package startech

import http.HttpUtil
import org.apache.commons.lang.math.NumberUtils
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Cell
import startech.services.ProductService
import util.DB


class test {
    static Map readXLSRow(HSSFRow row) {
        Iterator cells = row.cellIterator();
        Map<Integer, Object> cellValues = [:]
        while (cells.hasNext()) {
            HSSFCell cell = (HSSFCell) cells.next();
            if (cell.getCellTypeEnum() == CellType.STRING) {
                cellValues.put(cell.columnIndex, cell.getStringCellValue().trim())
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                cellValues.put(cell.columnIndex, cell.getNumericCellValue())
            } else if(cell.getCellTypeEnum() == CellType.BLANK){
                cellValues.put(cell.columnIndex, "")
            } else {
                println("Invalid Cell")
            }
        }
        return cellValues
    }

    static List readXLSSheet(HSSFSheet sheet, String fileName) {
        ProductService productService = new ProductService(null)
        Iterator rows = sheet.rowIterator();
        List rowValues = []
        Map<Integer, String> headers = [:]
        if(rows.hasNext()) {
            readXLSRow(rows.next()).each {key, value ->
                String header = value.toString().toLowerCase().replaceAll("\\s", "_")
                headers.put(header, key)
            }
        }

        while (rows.hasNext()) {
            Row row = rows.next()
            Cell productIdCell = row.getCell(headers.product_id)
            if(productIdCell) {
                Integer productId = productIdCell.getNumericCellValue();
                Map product = productService.getProduct(productId)
                if(product) {
                    row.getCell(headers.stock_status).setCellValue(product.stock_status)
                }
            }
        }
        return rowValues
    }

    static Map readXLSFile(File file) throws IOException {
        InputStream ExcelFileToRead = new FileInputStream(file)
        Map sheetIndex = [:]
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
        Iterator<HSSFSheet> iterator = wb.iterator()
        while (iterator.hasNext()) {
            HSSFSheet sheet = iterator.next()
            readXLSSheet(sheet, file.name)

        }
        FileOutputStream fileOut = new FileOutputStream("c:\\MyDrive\\black_friday.xls");
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    static void update(File exlFile) {
        readXLSFile(exlFile)

    }



    static void main(String[] args) {
        update(new File("c:\\MyDrive\\Black Friday.xls"))
    }
}

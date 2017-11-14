package startech

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType

class OCPriceSheetUpdater {
    final private static int PARENT_CATEGORY = 237;
    final private static String NAME = "printers-price-update";

    static List readXLSRow(HSSFRow row) {
        Iterator cells = row.cellIterator();
        List<Object> cellValues = []
        while (cells.hasNext()) {
            HSSFCell cell = (HSSFCell) cells.next();
            if (cell.getCellTypeEnum() == CellType.STRING) {
                cellValues.add(cell.getStringCellValue().trim())
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                cellValues.add(cell.getNumericCellValue())
            } else {
                println("Breaking Point")
            }
        }
        return cellValues
    }

    static List readXLSSheet(HSSFSheet sheet) {
        Iterator rows = sheet.rowIterator();
        List rowValues = []
        List<String> headers = []
        if(rows.hasNext()) {
            readXLSRow(rows.next()).each {
                String header = it.toString()
                headers.add(header.toLowerCase().replaceAll("\\s", "_"))
            }
        }

        while (rows.hasNext()) {
            Map rowValue = [:]
            readXLSRow(rows.next()).eachWithIndex { Object entry, int i ->
                String key = headers[i]
                key && (rowValue[key] = entry)
            }
            rowValues.add(rowValue)
        }
        rowValues
    }

    static Map readXLSFile(InputStream ExcelFileToRead) throws IOException {
        Map sheetIndex = [:]
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
        Iterator<HSSFSheet> iterator = wb.iterator()
        while (iterator.hasNext()) {
            HSSFSheet sheet = iterator.next()
            sheetIndex[sheet.getSheetName()] = readXLSSheet(sheet)

        }
        return sheetIndex
    }

    static Boolean skip(Map product) {
        if(product.new_price) {
            return false
        }

        if(product.new_status == "ok") {
            return true
        }

        if(product.new_price == "" && product.new_status == "") {
            return true
        }


    }

    static void main(String[] args) {
        Map<String, List> map = readXLSFile(new FileInputStream("C:\\MyDrive\\${NAME}.xls"))
        StringWriter writer = new StringWriter();
        map.each { String key, List<Map> values ->
            values.each { Map value ->
                if(skip(value)) { return }
                String updateSql = ""
                if(value.new_status == "delete") {
                    updateSql = "`status`='0'"
                } else if(value.new_status == "In Stock") {
                    updateSql = "`status`='1', quantity = '100'"
                }

                if(value.new_price) {
                    updateSql && (updateSql = "$updateSql, ")
                    Double newPrice = Double.parseDouble(value.new_price.toString())
                    updateSql = "$updateSql `price`='$newPrice'"
                }
                updateSql = "UPDATE `sr_product` SET $updateSql WHERE  `product_id`=${value.product_id};\n"
                writer.write(updateSql)
            }
        }
        File updateSqlFile = new File("c:\\MyDrive\\${NAME}.sql")
        updateSqlFile.text = writer.toString()
        println()
    }
}

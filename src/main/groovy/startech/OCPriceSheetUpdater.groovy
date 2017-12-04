package startech

import http.HttpUtil
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import util.DB

class OCPriceSheetUpdater {
    final private static String NAME = "Hard Disk Drive-Price Sheet- 30-11-17";
    final private static String host = "http://www.startech.com.bd/";
    final private static String operatorEmail = "sajid@startechbd.com";
    final private static String operatorPass = "ASDFG;lkjh";


    static List readXLSRow(HSSFRow row) {
        Iterator cells = row.cellIterator();
        List<Object> cellValues = []
        while (cells.hasNext()) {
            HSSFCell cell = (HSSFCell) cells.next();
            if (cell.getCellTypeEnum() == CellType.STRING) {
                cellValues.add(cell.getStringCellValue().trim())
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                cellValues.add(cell.getNumericCellValue())
            } else if(cell.getCellTypeEnum() == CellType.BLANK){
                cellValues.add("")
            } else {
                println("Invalid Cell")
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
            List row = readXLSRow(rows.next());
            if(row.size() < 6) {
                println("Invalid Row: " + row.toString())
                continue
            }
            row.eachWithIndex { Object entry, int i ->
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
        String encoding = Base64.getEncoder().encodeToString("$operatorEmail:$operatorPass".getBytes());
        DB db = new DB("startech");
        Map stockStatusIndex = [:]
        db.getResult("select * from sr_stock_status").each {
            String name = it.name.trim()
            name = name.toLowerCase().replaceAll("\\s+", "_")
            stockStatusIndex[name] = it.stock_status_id
        }
        Map<String, List> map = readXLSFile(new FileInputStream("C:\\MyDrive\\${NAME}.xls"))
        StringWriter writer = new StringWriter();
        map.each { String key, List<Map> values ->
            values.each { Map value ->
                if(skip(value)) { return }
                String newStatus  = value.new_status ?: ""
                newStatus = newStatus.toLowerCase().trim().replaceAll("\\s+", "_");
                if(newStatus && newStatus != "delete" && !stockStatusIndex.containsKey(newStatus)) {
                    println("Invalid Entry Product id: ${value.product_id}, Name: ${value.name}")
                    return
                } else if(newStatus != "delete") {
                    newStatus = stockStatusIndex[newStatus]
                }
                Double newPrice = value.new_price ? Double.parseDouble(value.new_price.toString()) : null
                String response = HttpUtil.doPostRequest("${host}index.php?route=operator/product_update_request/add", [
                        product_id: value.product_id,
                        new_status: newStatus,
                        new_price: newPrice
                ], ['Authorization': "Basic " + encoding])
                println(response)

            }
        }
    }
}

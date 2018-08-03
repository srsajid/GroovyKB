package startech

import org.apache.commons.lang.StringEscapeUtils
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import util.DB

class OCBrandMetaUpdater {
    private static final String DB_NAME = "mutho_phone";

    public static void main(String[] args) {

        DB db = new DB(DB_NAME)
        List<Map> results = db.getResult("select m.manufacturer_id, m.name, md.meta_title, md.meta_description from sr_manufacturer_description md left join sr_manufacturer m on md.manufacturer_id = m.manufacturer_id")
        File updateSqlFile = new File("c:\\MyDrive\\update_sql_file.sql");
        StringWriter writer = new StringWriter();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("meta");
        HSSFRow row = sheet.createRow(0);
        ["Name",  "Title", "Description", "Title Char Count", "Description Char Count"].eachWithIndex { String entry, int j ->
            HSSFCell cell = row.createCell(j);
            cell.setCellValue(entry)
        }
        results.eachWithIndex { Map result, int i ->
            String manufacturerId = result.manufacturer_id, name = result.name.trim()
            name = StringEscapeUtils.unescapeHtml(name)
            String metaTitle = "$name Bangladesh | Mutho Phone"
            String metaDescription = "Are you looking for $name  products in Bangladesh? Compare and find the best price for $name products"
            String sql = "update sr_manufacturer_description set meta_title = '${StringEscapeUtils.escapeSql(metaTitle)}', meta_description = '${StringEscapeUtils.escapeSql(metaDescription)}' where manufacturer_id = $manufacturerId;\n"
            writer.write(sql)
            row = sheet.createRow(i + 1);
            [name, metaTitle, metaDescription, metaTitle.length().toString(), metaDescription.length().toString()].eachWithIndex { String entry, int j ->
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(entry)
            }
        }
        updateSqlFile.text = writer.toString()
        FileOutputStream fileOut = new FileOutputStream("c:\\MyDrive\\meta.xls");
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }
}

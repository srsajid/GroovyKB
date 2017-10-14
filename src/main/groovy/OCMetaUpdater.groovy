import org.apache.commons.lang.StringEscapeUtils
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import util.DB

class OCMetaUpdater {


    public static void main(String[] args) {

        DB db = new DB("startech")
        List<Map> results = db.getResult("select d.product_id, d.name, p.model, d.meta_title, d.meta_description from sr_product_description d left join sr_product p on d.product_id = p.product_id")
        File updateSqlFile = new File("d:\\update_sql_file.sql");
        StringWriter writer = new StringWriter();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("meta");
        HSSFRow row = sheet.createRow(0);
        ["Name", "Model", "Title", "Description", "Title Char Count", "Description Char Count"].eachWithIndex { String entry, int j ->
            HSSFCell cell = row.createCell(j);
            cell.setCellValue(entry)
        }
        results.eachWithIndex { Map result, int i ->
            String productId = result.product_id, name = result.name.trim(), model = result.model?.trim() ?: name
            name = name.replaceAll("&quot;", '"')
            model = model.replaceAll("&quot;", '"')
            String metaTitle = "$model Price in Bangladesh | Star Tech"
            String metaDescription = "Buy $name at competitive price in Bangladesh. Order online or visit your nearest Star Tech branch"
            String sql = "update sr_product_description set meta_title = '${StringEscapeUtils.escapeSql(metaTitle)}', meta_description = '${StringEscapeUtils.escapeSql(metaDescription)}' where product_id = $productId;\n"
            writer.write(sql)
            row = sheet.createRow(i + 1);
            [name, model, metaTitle, metaDescription, metaTitle.length().toString(), metaDescription.length().toString()].eachWithIndex { String entry, int j ->
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(entry)
            }
        }
        updateSqlFile.text = writer.toString()
        FileOutputStream fileOut = new FileOutputStream("d:\\meta.xls");
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }
}

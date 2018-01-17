package startech

import http.HttpUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import util.DB;

import javax.sql.DataSource;

public class LocalDatabaseUpdater {
    final private static String host = "https://www.startech.com.bd/admin/";
    final private static String operatorEmail = "startech";
    final private static String operatorPass = "";

    synchronized void updateDatabase() {
        String encoding = Base64.getEncoder().encodeToString("$operatorEmail:$operatorPass".getBytes());
        InputStream inputStream = HttpUtil.getPostConnection("${host}index.php?route=tool/backup/backup", [
                "backup[]": [
                        "sr_product",
                        "sr_product_description",
                        "sr_category",
                        "sr_category_description",
                        "sr_url_alias",
                        "sr_product_special",
                        "sr_product_to_category",
                        "sr_stock_status"
                ]
        ], ['Authorization': "Basic " + encoding]).inputStream

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new InputStreamResource(inputStream))
        populator.populate(new DB("startech").getConnection())
    }

    public static void main(String[] args) {
        new LocalDatabaseUpdater().updateDatabase()
    }
}

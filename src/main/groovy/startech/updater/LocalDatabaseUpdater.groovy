package startech.updater

import http.HttpUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import util.DB

public class LocalDatabaseUpdater {
    final private static String HOST = "www.startech.com.bd";
    final private static String operatorEmail = "srsajid";
    final private static String operatorPass = "";

    static advance =  [
            "sr_attribute",
            "sr_attribute_description",
            "sr_attribute_group",
            "sr_attribute_group_description",
            "sr_attribute_profile",
            "sr_filter_profile",
            "sr_filter_group",
            "sr_filter_group_description",
            "sr_filter",
            "sr_filter_description",
            "sr_filter_description",
            "sr_stock_status",
            "sr_manufacturer",
            "sr_manufacturer_description",
            "sr_category",
            "sr_category_description",
            "sr_category_path",
            "sr_category_manufacturer",
            "sr_category_manufacturer_description",
            "sr_product",
            "sr_product_description",
            "sr_product_special",
            "sr_url_alias",
            "sr_product_to_category",
            "sr_product_attribute",
            "sr_product_filter",
            "sr_product_to_store",
            "sr_product_related"
    ]

    static simple = [
            "sr_product",
            "sr_product_description",
            "sr_category",
            "sr_category_description",
            "sr_product_to_category",
            "sr_category_to_store",
            'sr_category_manufacturer',
            'sr_category_manufacturer_description',
            "sr_url_alias"
    ]


    synchronized void updateDatabase(DB db, List tables, String host = null) {
        host = host ?: HOST
        Scanner scanner = new Scanner(System.in);
        print("Enter Pass:")
        String password = scanner.nextLine();
        String encoding = Base64.getEncoder().encodeToString("$operatorEmail:$password".getBytes());

        tables.each {
            InputStream inputStream = HttpUtil.getPostConnection("http://${host}/admin/index.php?route=tool/backup/backup", [
                    "backup[]": it
            ], ['Authorization': "Basic " + encoding]).inputStream

            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new InputStreamResource(inputStream))
            populator.populate(db.getConnection())
        }
    }


    public static void main(String[] args) {
        List tables = args[1] == "advance" ? advance : simple
        new LocalDatabaseUpdater().updateDatabase(new DB(args[0]), tables)
    }
}

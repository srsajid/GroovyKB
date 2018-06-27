package startech

import http.HttpUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import util.DB;

import javax.sql.DataSource;

public class LocalDatabaseUpdater {
    final private static String host = "https://www.startech.com.bd/admin/";
    final private static String operatorEmail = "srsajid";
    final private static String operatorPass = "";

    static tableList1 =  [
            "sr_product",
            "sr_product_description",
            "sr_category",
            "sr_category_description",
            "sr_url_alias",
            "sr_product_special",
            "sr_product_to_category",
            "sr_stock_status",
            "sr_order",
            "sr_order_custom_field",
            "sr_order_history",
            "sr_order_product",
            "sr_order_option",
            "sr_order_recurring",
            "sr_order_recurring_transaction",
            "sr_order_status",
            "sr_order_total",
            "sr_order_voucher",
            "sr_attribute",
            "sr_attribute_description",
            "sr_attribute_group",
            "sr_attribute_group_description",
            "sr_attribute_profile",
            "sr_product_attribute",
            "sr_manufacturer",
            "sr_manufacturer_description",
    ]

    static tableList2 = [
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


    synchronized void updateDatabase(String database, List tables) {
        Scanner scanner = new Scanner(System.in);
        print("Enter Pass:")
        String password = scanner.nextLine();
        String encoding = Base64.getEncoder().encodeToString("$operatorEmail:$password".getBytes());

        tables.each {
            InputStream inputStream = HttpUtil.getPostConnection("${host}index.php?route=tool/backup/backup", [
                    "backup[]": it
            ], ['Authorization': "Basic " + encoding]).inputStream

            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new InputStreamResource(inputStream))
            populator.populate(new DB(database).getConnection())
        }
    }

    public static void main(String[] args) {
        List tables = args[1] == "advance" ? tableList1 : tableList2
        new LocalDatabaseUpdater().updateDatabase(args[0], tables)
    }
}

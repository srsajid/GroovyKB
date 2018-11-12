package startech.updater

import http.HttpUtil
import startech.services.CategoryService
import startech.services.ProductService

class ParentUpdater {
    static String HOST =  "https://www.startech.com.bd/"
    public static void main(String[] args) {
        CategoryService categoryService = new CategoryService()
        ProductService productService = new ProductService()
        List list = categoryService.getLeafCategories()
        Scanner scanner = new Scanner(System.in);
        print("Enter Pass:")
        String password = scanner.nextLine();
        String encoding = Base64.getEncoder().encodeToString("srsajid:$password".getBytes());

        list.each {Map parent ->
            List products = productService.getProducts([
                    category_id: parent.category_id,
                    manufacturer_id: parent.manufacturer_id
            ]);
            products.each {
                Map data = [
                        product_id: it.product_id,
                        parent_id: parent.category_id,
                        is_manucaturer_is_parent: parent.manufacturer_id ? 1 : 0
                ]
                InputStream inputStream = HttpUtil.getPostConnection("${HOST}admin/index.php?route=catalog/product/setParent",
                        data, ['Authorization': "Basic " + encoding]).inputStream
                println(new InputStreamReader(inputStream).text)
            }

        }
    }
}

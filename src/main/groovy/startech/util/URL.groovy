package startech.util

import util.DB;
public class URL {
    DB db
    public URL(DB db) {
        this.db = db
    }
    String rewrite(Map $data) {
        String $url = '';
        $data.each { String $key, String $value ->
            if ($data['route']) {
                if (($data['route'] == 'product/product' && $key == 'product_id') || (($data['route'] == 'product/manufacturer/info' || $data['route'] == 'product/product') && $key == 'manufacturer_id') || ($data['route'] == 'information/information' && $key == 'information_id')) {
                    List $query = this.db.getResult("SELECT * FROM sr_url_alias WHERE `query` = '" + $key + '=' + $value + "'")

                    if ($query.size() && $query[0]['keyword']) {
                        $url += '/' + $query[0]['keyword'];
                    }
                } else if ($key == 'path') {
                    if($data['route'] != 'product/product') {
                        List $categories = $value.split('_');

                        for (String $category : $categories ) {
                            List $query = this.db.getResult("SELECT * FROM sr_url_alias WHERE `query` = 'category_id=" + $category + "'");
                            if ($query.size() && $query[0]['keyword']) {
                                $url += '/' + $query[0]['keyword'];
                            } else {
                                $url = '';
                                break;
                            }
                        }
                    }
                } else if ($data['route'] == 'common/home') {
                    $url += "/";
                }
            }
        }
        return 'https://www.startech.com.bd' + $url
    }
}

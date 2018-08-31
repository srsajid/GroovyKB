package startech.services

import org.springframework.web.util.UriUtils
import startech.util.URL
import util.DB

class ManufacturerService {
    DB db
    URL url

    ManufacturerService(DB db)  {
        this.db = db ?: new DB("startech")
        this.url = new URL(this.db)
    }

    Map getManufacturer(Integer id) {
        List<Map> results = db.getResult("select * from sr_manufacturer m left join sr_manufacturer_description md on md.manufacturer_id = m.manufacturer_id where m.manufacturer_id = $id")
        return results?.first() ?: null
    }

    Map getManufacturerByName(String name) {
        List<Map> results = db.getResult("select * from sr_manufacturer m left join sr_manufacturer_description md on md.manufacturer_id = m.manufacturer_id where m.name = '$name'")
        return results?.first() ?: null
    }

}

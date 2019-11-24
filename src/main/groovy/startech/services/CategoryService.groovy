package startech.services

import org.apache.commons.lang.StringEscapeUtils
import util.DB

class CategoryService {
    DB db

    CategoryService(DB db) {
        this.db = db ?: new DB("startech")
    }

    Map getCategory(category_id) {
        List<Map> results = db.getResult("select DISTINCT c.category_id, cd.name from  sr_category c left join sr_category_description cd on c.category_id = cd.category_id where c.category_id = $category_id and c.status = 1")
        return results ? results.first() : null
    }

    List getChildCategories(parent_id) {
        List results = db.getResult("select DISTINCT c.category_id, cd.name, c.sort_order from  sr_category c left join sr_category_description cd on c.category_id = cd.category_id where c.parent_id = $parent_id and c.status = 1 order by c.sort_order, cd.name")
        return results ?: []
    }

    List getCategoryManufacturer(parent_id) {
        return []
        List results = db.getResult("select DISTINCT cm.manufacturer_id, cm.category_id, cmd.name, cm.sort_order from  sr_category_manufacturer cm left join sr_category_manufacturer_description cmd on cm.category_manufacturer_id = cmd.category_manufacturer_id where cm.category_id = $parent_id order by cm.sort_order")
        return results ?: []
    }


    List getLeafCategories() {
        List results = [],  rootCategories = getChildCategories(0)
        rootCategories.each {rootCategory ->
            List secondLevelCategories = getChildCategories(rootCategory.category_id)
            secondLevelCategories.each {secondLevelCategory ->
                List thirdLevelCategories = getChildCategories(secondLevelCategory.category_id)
                thirdLevelCategories.each { thirdLevelCategory ->
                    results.add([
                            category_id: thirdLevelCategory.category_id,
                            name: StringEscapeUtils.unescapeHtml(rootCategory.name)  + " > " + StringEscapeUtils.unescapeHtml(secondLevelCategory.name ) + " > " + StringEscapeUtils.unescapeHtml(thirdLevelCategory.name)
                    ])
                }
                if(thirdLevelCategories.size() == 0) {
                    thirdLevelCategories = getCategoryManufacturer(secondLevelCategory.category_id)
                    thirdLevelCategories.each { thirdLevelCategory ->
                        results.add([
                                category_id: thirdLevelCategory.category_id,
                                manufacturer_id: thirdLevelCategory.manufacturer_id,
                                name: StringEscapeUtils.unescapeHtml(rootCategory.name)  + " > " + StringEscapeUtils.unescapeHtml(secondLevelCategory.name ) + " > " + StringEscapeUtils.unescapeHtml(thirdLevelCategory.name)
                        ])
                    }
                }
                if(thirdLevelCategories.size() == 0) {
                    results.add([
                            category_id: secondLevelCategory.category_id,
                            name: StringEscapeUtils.unescapeHtml(rootCategory.name)  + " > " + StringEscapeUtils.unescapeHtml(secondLevelCategory.name )
                    ])
                }
            }
            if(secondLevelCategories.size() == 0) {
                secondLevelCategories = getCategoryManufacturer(rootCategory.category_id)
                secondLevelCategories.each {secondLevelCategory ->
                    results.add([
                            category_id: secondLevelCategory.category_id,
                            manufacturer_id: secondLevelCategory.manufacturer_id,
                            name: StringEscapeUtils.unescapeHtml(rootCategory.name)  + " > " + StringEscapeUtils.unescapeHtml(secondLevelCategory.name )
                    ])
                }
            }
            if(secondLevelCategories.size() == 0) {
                results.add([
                        category_id: rootCategory.category_id,
                        name: StringEscapeUtils.unescapeHtml(rootCategory.name)
                ])
            }

        }
        return results
    }
}

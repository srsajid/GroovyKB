package startech.services

import org.apache.commons.lang.StringEscapeUtils
import util.DB

class CategoryService {
    DB db

    CategoryService(DB db) {
        this.db = db ?: new DB("startech")
    }

    List getChildCategories(parent_id) {
        List results = db.getResult("select DISTINCT c.category_id, cd.name from  sr_category c left join sr_category_description cd on c.category_id = cd.category_id where c.parent_id = $parent_id and c.status = 1")
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
                    results.add([
                            category_id: secondLevelCategory.category_id,
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

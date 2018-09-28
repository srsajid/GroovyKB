package org.jsoup.helper

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class Test {
    public static void main(String[] args) {
        String url = "https://www.gsmarena.com/apple_iphone_xs-9318.php"
        Document doc = SRHttpConnection.connect(url).get();
        Map specs = [:]
        Elements specGroups = doc.select("#specs-list > table")
        specGroups.each { group ->
            String groupName = group.select("th").text().trim().replaceAll(/\s/, "_").toLowerCase()
            group.select("tr").each { spec ->
                String name = spec.select(".ttl").text().trim().replaceAll(/\s/, "_").toLowerCase()
                specs[groupName + "_" + name]  = spec.select(".nfo").text().trim()
            }
        }
        println(specs)
    }
}

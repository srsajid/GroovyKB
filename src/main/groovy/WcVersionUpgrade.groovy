import groovy.json.JsonSlurper

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement

/**
 * Created by sajedur on 1/3/2017.
 */
class WcVersionUpgrade {
    final static String DATABASE_SERVER = "srv3.cmdr.bitmascot.com"
    final static String DATABASE_NAME = "wc_2ded2015_cmdr_bitmascot_com"
    final static String USER = "sadmin"
    final static String PASSWORD = "webcommander"
    final static String FROM_VERSION = ""
    final static String TO_VERSION = "2.2.1"
    final static String CODE_PATH = "D:\\codes\\extreme\\extreme2"
    final static List PLUGINS = ['ask-question', 'auto-product-load-on-scroll', 'blog', 'ebay-listing', 'eparcel-order-export', 'event-management', 'form-editor', 'get-price', 'gift-certificate', 'google-product', 'myob', 'my-shopping', 'gallery-owl-carousel', 'payway-payment-gateway', 'product-quick-view', 'product-review', 'shop-by-brand']

    static def getResult(Connection connection, String sql) {
        Statement statement = connection.createStatement();
        Boolean executeResult = statement.execute(sql);
        if(executeResult) {
            ResultSet resultSet = statement.getResultSet()
            List<Map> list = []
            ResultSetMetaData metaData = resultSet.getMetaData();
            Integer noOfColumn = metaData.getColumnCount()
            while (resultSet.next()) {
                Map map = [:]
                for (int i = 1; i <= noOfColumn; i++) {
                    map.put(metaData.getColumnName(i), resultSet.getString(i))
                }
                list.add(map)
            }
            return list
        } else  {
            return statement.getUpdateCount()
        }

    }

    public static runSql(Connection connection, Map sql, String developer) {
        try {
            def result = getResult(connection, sql.condition)
            String performSql
            if(result instanceof List && result.size()) {
                performSql = sql.isTrue
            } else  {
                performSql = sql.isFalse
            }
            def performSqlResult
            if(performSql) {
                performSqlResult = getResult(connection, performSql)
            }
            println('-------------------------------------------')
            println("Status: Success")
            println("Condition: ${sql.condition}")
            println("Perform SQL: ${performSql}")
            println("Result: " +  performSqlResult)
            println('-------------------------------------------')
        } catch (Exception ex) {
            println('-------------------------------------------')
            println("Status: error")
            println("Developer: ${developer}")
            println(sql)
            println('-------------------------------------------')
        }


    }

    public static runSqlFile(Connection connection, File coreJSONFile) {
        JsonSlurper slurper = new JsonSlurper()
        try {
            if(coreJSONFile.exists()) {
                println("\n\n************************************************")
                println("Executing File ${coreJSONFile.absolutePath}")
                println("************************************************\n\n")
                Map coreJson = slurper.parse(coreJSONFile)
                coreJson.developers.each {Map map ->
                    map.sql.each {Map sql ->
                        runSql(connection, sql, map.name)
                    }
                }
            } else {
                println("${coreJSONFile.absolutePath} not exist")
            }
        }
        catch (Exception ex) {
            println("${coreJSONFile.absolutePath} execution failed")
        }

    }

    public static void main(String[] args) {
        Class.forName("com.mysql.jdbc.Driver")
        String url = "jdbc:mysql://${DATABASE_SERVER}/${DATABASE_NAME}?characterEncoding=utf8&autoReconnect=true"
        Connection connection = DriverManager.getConnection(url, USER, PASSWORD)
        File coreJSONFile = new File("${CODE_PATH}\\web-app\\WEB-INF\\sql\\update", "${TO_VERSION}.json")
        runSqlFile(connection, coreJSONFile)
        PLUGINS.each {
            runSqlFile(connection, new File("${CODE_PATH}\\ref\\plugins\\${it}\\web-app\\WEB-INF\\sql\\update", "${TO_VERSION}.json"))

        }
    }
}

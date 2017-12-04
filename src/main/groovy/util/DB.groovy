package util

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement

class DB {
    String server = "localhost"
    String user = "root"
    String password = ""
    String database
    Connection connection

    public DB(String database) {
        this.database = database
        Class.forName("com.mysql.jdbc.Driver")
        String url = "jdbc:mysql://${server}/${database}?characterEncoding=utf8&autoReconnect=true"
        connection = DriverManager.getConnection(url, user, password)
    }

    def getResult(String sql) {
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
                    String value = null
                    try {
                        value = resultSet.getString(i)
                    } catch (Exception ex) {}
                    map.put(metaData.getColumnName(i), value)
                }
                list.add(map)
            }
            return list
        } else  {
            return statement.getUpdateCount()
        }
    }

    def insert(String sql, List args) {
        PreparedStatement statement =  connection.prepareStatement(sql)
        args.eachWithIndex { Object entry, int i ->
            if(entry instanceof String) {
                statement.setString(i+1, entry)
            } else if(entry instanceof Double) {
                statement.setDouble(i+1, entry)
            } else if(entry instanceof Float ) {
                statement.setFloat(i+1, entry)
            } else if(entry instanceof Integer) {
                statement.setInt(i+1, entry)
            }
        }
        statement.execute();
        return statement.updateCount
    }

    void close() {
        connection.close()
    }
}

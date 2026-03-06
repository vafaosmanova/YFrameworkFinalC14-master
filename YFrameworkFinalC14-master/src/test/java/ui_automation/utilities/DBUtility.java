package ui_automation.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ui_automation.constants.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtility {

    public static final Logger LOGGER = LogManager.getLogger(DBUtility.class);
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void openConnection() {

        String url = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_DB_URL);
        String username = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_DB_USERNAME);
        String password = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_DB_PASSWORD);
        String dbDriver = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_DB_DRIVER);

        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Map<String, Object>> executeSQLQuery(String query) {

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            // getMetaData() is a dynamic way of getting all the columns from that table
            ResultSetMetaData metaData = resultSet.getMetaData();
            int totalColumnCount = metaData.getColumnCount();
            // Empty List of Maps where we will store all the records
            List<Map<String, Object>> listOfMap = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= totalColumnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object object = resultSet.getObject(i);
                    map.put(columnName, object);
                }
                listOfMap.add(map);
            }
            return listOfMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}









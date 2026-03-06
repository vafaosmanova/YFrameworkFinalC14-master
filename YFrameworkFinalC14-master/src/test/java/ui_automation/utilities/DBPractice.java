package ui_automation.utilities;

import ui_automation.constants.Constants;

import java.sql.*;

public class DBPractice {

    public static void main(String[] args) {

        // To create a connection to database and execute some statements we will need to follow few steps:
        // 1. Load and register the JDBC driver for specific DB in your JVM
        // 2. Create and establish the database connection
        // 3. Create a SQL Query - Statement
        // 4. Execute the SQL statement and get the results
        // 5. Process the results (do conditions and manipulate data)
        // 6. Close all connections!

        // We need to setup all the credentials for db connection
        //String url = "jdbc:sqlserver://database-1.cd2kmrv4yrcl.us-east-1.rds.amazonaws.com;databaseName=mealb";
        // String url = ConfigurationReader.getProperty("ui-config.properties", "mb.database.url");
        String url = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_DB_URL);
        // String username = "student";
        String username = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_DB_USERNAME);
        // String password = "Student2022Q";
        String password = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_DB_PASSWORD);
        // String dbDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbDriver = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_DB_DRIVER);

        // 1. Load and register the JDBC driver for specific DB in your JVM
        try{
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        // 2. Create and establish the database connection
        String query = "SELECT * FROM Expenses";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            while(resultSet.next()){
                // retrieve the data that you need once by one
                int id = resultSet.getInt("Id");
                double amount = resultSet.getDouble("Amount");
                String name = resultSet.getString("Name");
                int creatorId = resultSet.getInt("CreatorUserId");

                if(creatorId == 12572 && name.equalsIgnoreCase("special gift")){
                    System.out.println("| ID: " + id + " | Name: " + name + " | Amount: " + amount);
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }



        // Legacy way of handling resources and closing those
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//        try{
//            // connection
//             connection = DriverManager.getConnection(url, username, password);
//            // create statement
//             statement = connection.createStatement();
//            // create a query
//            String query = "SELECT * FROM Expenses";
//            // execute statement
//             resultSet = statement.executeQuery(query);
//
//            // loop over the results
//            while(resultSet.next()){
//                // retrieve the data that you need once by one
//                int id = resultSet.getInt("Id");
//                double amount = resultSet.getDouble("Amount");
//                String name = resultSet.getString("Name");
//                int creatorId = resultSet.getInt("CreatorUserId");
//
//                if(creatorId == 12572 && name.equalsIgnoreCase("special gift")){
//                    System.out.println("| ID: " + id + " | Name: " + name + " | Amount: " + amount);
//                }
//            }
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        } finally {
//            try {
//                resultSet.close();
//                statement.close();
//                connection.close();
//            } catch (SQLException e){
//                e.printStackTrace();
//            }
//
//        }

    }

}

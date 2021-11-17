package com.j3portfolio.database.handler;


import java.sql.*;

public class DatabaseHandler {
    private static String url = "jdbc:sqlite:database.db";

    public static Connection Connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection established to " + url);
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }

    public static void TestDatabase() {
        String sql = "SELECT id, username, passHash, saltHash FROM UserLogin";

        try(Connection connection = Connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                System.out.println(resultSet.getInt("id") + "\t"
                        + resultSet.getString("username") + "\t"
                        + resultSet.getString("passHash") + "\t"
                        + resultSet.getInt("saltHash"));
            }
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

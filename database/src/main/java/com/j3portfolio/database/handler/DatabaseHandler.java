package com.j3portfolio.database.handler;


import com.j3portfolio.database.standards.Password;
import com.j3portfolio.database.standards.User;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String url = "jdbc:sqlite:database.db";

    private static final String baseUserLoginSQL = "SELECT id, username, passHash, saltHash FROM UserLogin";

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

    public static User GetUserByID(int id) {
        String query = baseUserLoginSQL + " WHERE id = " + id + ";";
        ResultSet resultSet = null;
        List<User> users = null;
        try {
            resultSet = ExecuteQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if(resultSet == null)
            return null;
        users = ConvertResultSetToUsers(resultSet);
        if(users.isEmpty())
            return null;
        return users.get(0);
    }

    public static List<User> GetUsersByUsername(String username) {
        String query = baseUserLoginSQL + " WHERE username LIKE \'" + username + "%\';";
        System.out.println(query);
        ResultSet resultSet = null;
        List<User> users = null;
        try {
            resultSet = ExecuteQuery(query);
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if(resultSet == null)
            return null;
        users = ConvertResultSetToUsers(resultSet);
        if(users.isEmpty())
            return null;
        return users;
    }

    private static List<User> ConvertResultSetToUsers(ResultSet resultSet) {
        List<User> users = new ArrayList<User>();

        try {
            while(resultSet.next()) {
                users.add(new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("username"), new Password(resultSet.getString("passHash"), resultSet.getInt("saltHash"))));
            }
            return users;
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private static ResultSet ExecuteQuery(String query) throws SQLException {
        Connection connection = Connect();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    }
}

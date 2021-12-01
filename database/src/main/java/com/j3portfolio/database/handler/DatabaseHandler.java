package com.j3portfolio.database.handler;


import com.j3portfolio.database.standards.Password;
import com.j3portfolio.database.standards.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String url = "jdbc:sqlite:database.db";
    private static final String baseUserLoginSQL = "SELECT id, username, email, passHash, saltHash FROM UserLogin";
    private static final String baseUserSettingsSQL = "SELECT "; // LATER
    private static final String baseProfileSQL = "SELECT id"; // LATER

    private static Connection Connect() {
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
        return GetUsersByQuery(query).get(0);
    }

    public static boolean AddUser(User user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        String username = user.getUsername();
        String email = user.getEmail();
        String query = "INSERT INTO UserLogin(username, email, passHash, saltHash) VALUES("
                + username + ", "
                + email + ", "
                + user.getPassHash() + ", "
                + user.getSaltHash() + ");";
        CheckIfUserExists(username, email);
        return true;
    }

    private static void CheckIfUserExists(String username, String email) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        String usernameQuery = baseUserLoginSQL + " WHERE username = " + username + ";";
        String emailQuery = baseUserLoginSQL + " WHERE email = " + email + ";";

        if(ExecuteQuery(usernameQuery) != null)
            throw new UsernameAlreadyExistsException(username);
        else if(ExecuteQuery(emailQuery) != null)
            throw new EmailAlreadyExistsException(email);
    }

    private static User GetUserByExactUsername(String username) {
        String query = baseUserLoginSQL + " WHERE username =" + username + ";";
        return GetUsersByQuery(query).get(0);
    }

    private static User GetUserByExactEmail(String email) {
        String query = baseUserLoginSQL + " WHERE email =" + email + ";";
        return GetUsersByQuery(query).get(0);
    }

    public static List<User> GetUsersByUsername(String username) {
        String query = baseUserLoginSQL + " WHERE username LIKE \'" + username + "%\';";
        return GetUsersByQuery(query);
    }

    private static List<User> GetUsersByQuery(String query) {
        List<User> users = null;
        ResultSet resultSet = ExecuteQuery(query);
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

    private static ResultSet ExecuteQuery(String query) {
        Connection connection = Connect();
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return resultSet;
    }

    public static class UsernameAlreadyExistsException extends Exception {
        public UsernameAlreadyExistsException(String msg) {
            super("Username: [" + msg + "] already exists in the database.");
        }
    }

    public static class EmailAlreadyExistsException extends Exception {
        public EmailAlreadyExistsException(String msg) {
            super("Email: [" + msg + "] already exists in the database.");
        }
    }
}
package com.j3portfolio.database.handler;


import com.j3portfolio.database.standards.Password;
import com.j3portfolio.database.standards.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
JAVASCRIPT REST SIDE CODE
const soap = require('soap')
const url = 'http://localhost:8080/userservice?wsdl'
const pas = {passHash: 'sadfjkhsafdjh', saltHash: 23482734}
const args = {username: 'User', email: 'User@gmail.com', password: pas}

soap.createClient(url, function(err, client) {
    client.addUser(args, function(err, result) {
        console.log(result);
    })
})

*/

public class DatabaseHandler {
    private static final String url = "jdbc:sqlite:database.db";
    private static final String baseUserLoginSQL = "SELECT * FROM UserLogin";
    private static final String baseUserSettingsSQL = "SELECT "; // LATER
    private static final String baseProfileSQL = "SELECT id"; // LATER

    private static Connection Connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }

    public static User GetUserByID(int id) {
        String query = baseUserLoginSQL + " WHERE id = " + id + ";";
        List<User> users = GetUsersByQuery(query);
        if(users != null)
            return GetUsersByQuery(query).get(0);
        return null;
    }

    public static int AddUser(User user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        String username = user.getUsername();
        String email = user.getEmail();
        String query = "INSERT INTO UserLogin(username, email, passHash, saltHash) VALUES(\'"
                + username + "\', \'"
                + email + "\', \'"
                + user.getPassHash() + "\', "
                + user.getSaltHash() + ");";
        CheckIfUserExists(username, email);
        ExecuteQuery(query);
        User userID = GetUserByExactUsername(username);
        return userID.getId();
    }

    private static void CheckIfUserExists(String username, String email) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        if(GetUserByExactUsername(username) != null)
            throw new UsernameAlreadyExistsException(username);
        else if(GetUserByExactEmail(email) != null)
            throw new EmailAlreadyExistsException(email);
    }

    private static User GetUserByExactUsername(String username) {
        String query = baseUserLoginSQL + " WHERE username =\'" + username + "\';";
        List<User> users = GetUsersByQuery(query);
        if(users != null)
            return GetUsersByQuery(query).get(0);
        return null;
    }

    private static User GetUserByExactEmail(String email) {
        String query = baseUserLoginSQL + " WHERE email =\'" + email + "\';";
        List<User> users = GetUsersByQuery(query);
        if(users != null)
            return GetUsersByQuery(query).get(0);
        return null;
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
            // IGNORE (RESULT SET DOES NOT EXIST)
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
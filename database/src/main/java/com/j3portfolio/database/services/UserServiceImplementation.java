package com.j3portfolio.database.services;

import com.j3portfolio.database.handler.DatabaseHandler;
import com.j3portfolio.database.standards.Password;
import com.j3portfolio.database.standards.User;

import javax.jws.WebService;

@WebService(name = "UserService", endpointInterface = "com.j3portfolio.database.services.UserService", targetNamespace = "http://userservice.services.database.j3portfolio.com/")
public class UserServiceImplementation implements UserService {

    @Override
    public User getUserByID(int id) {
        User user = DatabaseHandler.GetUserByID(id);
        if(user != null)
            return user;
        return new User(-1, "-1", "-1", new Password("-1", -1));
    }

    @Override
    public String getUserName(int id) {
        User user = DatabaseHandler.GetUserByID(id);
        if(user != null)
            return user.getUsername();
        return "-1"; // Should never happen if passport is done correctly
    }

    @Override
    public User getUserID(String email) {
        User user = DatabaseHandler.GetUserByExactEmail(email);
        if(user != null)
            return user;
        return new User(-1, "-1", "-1", new Password("-1", -1));
    }

    @Override
    public Password getPassword(int id) {
        User user = DatabaseHandler.GetUserByID(id);
        if(user != null)
            return user.getPassword();
        return new Password("-1", -1);
    }

    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public int addUser(String email, String username, Password password) {
        try {
            return DatabaseHandler.AddUser(new User(username, email, password));
        } catch (DatabaseHandler.UsernameAlreadyExistsException e) {
            return -1;
        } catch (DatabaseHandler.EmailAlreadyExistsException e) {
            return -2;
        }
    }

    @Override
    public boolean updatePassword(int id, Password password) {
        DatabaseHandler.UpdatePassword(id, password);
        return true;
    }

    @Override
    public boolean updateUsername(int id, String username) {
        try {
            DatabaseHandler.UpdateUsername(id, username);
        } catch (DatabaseHandler.UsernameAlreadyExistsException e) {
            return false;
        }
        return true;
    }
}

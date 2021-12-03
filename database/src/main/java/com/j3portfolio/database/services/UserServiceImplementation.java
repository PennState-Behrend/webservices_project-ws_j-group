package com.j3portfolio.database.services;

import com.j3portfolio.database.handler.DatabaseHandler;
import com.j3portfolio.database.standards.Password;
import com.j3portfolio.database.standards.User;

import javax.jws.WebService;

@WebService(name = "UserService", endpointInterface = "com.j3portfolio.database.services.UserService", targetNamespace = "http://userservice.services.database.j3portfolio.com/")
public class UserServiceImplementation implements UserService {

    @Override
    public Password getPassword(int id) {
        return null;
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
        return false;
    }

    @Override
    public boolean updateUsername(int id, String username) {
        return true;
    }
}

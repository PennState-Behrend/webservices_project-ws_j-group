package com.j3portfolio.database.services;

import com.j3portfolio.database.standards.Password;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
        name = "UserService",
        endpointInterface = "com.j3portfolio.database.services.UserService",
        targetNamespace = "http://userservice.services.database.j3portfolio.com/"
)
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
    public int addUser(String username, Password password) {
        System.out.println("Username: " + username + ", Password: " + password);
        return -1;
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

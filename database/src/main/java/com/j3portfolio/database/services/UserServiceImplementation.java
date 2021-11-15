package com.j3portfolio.database.services;

import com.j3portfolio.database.standards.Password;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
        name = "UserService",
        endpointInterface = "com.j3portfolio.database.services.UserServiceImplementation",
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
    public boolean addUser(int id) {
        return false;
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

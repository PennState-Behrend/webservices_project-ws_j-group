package com.j3portfolio.database.services;

import com.j3portfolio.database.standards.Password;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface UserService {
    @WebMethod
    Password getPassword(int id);

    @WebMethod
    boolean deleteUser(int id);

    @WebMethod
    boolean addUser(int id);

    @WebMethod
    boolean updatePassword(int id, Password password);

    @WebMethod
    boolean updateUsername(int id, String username);
}

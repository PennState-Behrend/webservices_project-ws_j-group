package com.j3portfolio.database.services;

import com.j3portfolio.database.standards.Password;
import com.j3portfolio.database.standards.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface UserService {
    @WebMethod
    User getUserByID(@WebParam(name = "id") int id);

    @WebMethod
    String getUserName(@WebParam(name = "id") int id);

    @WebMethod
    User getUserID(@WebParam(name = "email") String email);

    @WebMethod
    Password getPassword(@WebParam(name = "id") int id);

    @WebMethod
    boolean deleteUser(@WebParam(name = "id") int id);

    @WebMethod
    int addUser(@WebParam(name = "username") String username, @WebParam(name = "email") String email, @WebParam(name = "password") Password password);

    @WebMethod
    boolean updatePassword(@WebParam(name = "id") int id, @WebParam(name = "password") Password password);

    @WebMethod
    boolean updateUsername(@WebParam(name = "id") int id, @WebParam(name = "username") String username);
}

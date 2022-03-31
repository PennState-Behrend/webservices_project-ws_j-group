package com.j3portfolio.database;

import com.j3portfolio.database.handler.DatabaseHandler;
import com.j3portfolio.database.services.UserServiceImplementation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.ws.Endpoint;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/userservice", new UserServiceImplementation());
    }

}

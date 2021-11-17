package com.j3portfolio.database;

import com.j3portfolio.database.handler.DatabaseHandler;
import com.j3portfolio.database.services.UserServiceImplementation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.ws.Endpoint;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DatabaseApplication.class, args);

        DatabaseHandler.TestDatabase();
        Endpoint.publish(
                "http://localhost:8080/userservice",
                new UserServiceImplementation()
        );
    }

}

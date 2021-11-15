package com.j3portfolio.database;

import com.j3portfolio.database.services.UserServiceImplementation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.ws.Endpoint;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DatabaseApplication.class, args);

        Endpoint.publish(
                "http://localhost:80/userservice",
                new UserServiceImplementation()
        );
    }

}

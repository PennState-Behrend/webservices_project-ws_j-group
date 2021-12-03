package com.j3portfolio.database.standards;

public class User {
    private int id;
    private String email;
    private String username;
    private Password password;

    public User(int id, String email, String username, Password password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String email, String username, Password password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassHash() {
        return password.getPassHash();
    }

    public void setPassHash(String passHash) {
        this.password.setPassHash(passHash);
    }

    public int getSaltHash() {
        return password.getSaltHash();
    }

    public void setSaltHash(int saltHash) {
        this.password.setSaltHash(saltHash);
    }
}

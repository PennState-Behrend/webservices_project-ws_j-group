package com.j3portfolio.database.standards;

public class Password {
    private String passHash;
    private int saltHash;

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public int getSaltHash() {
        return saltHash;
    }

    public void setSaltHash(int saltHash) {
        this.saltHash = saltHash;
    }
}
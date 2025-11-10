package com.tns.onlineshopping.entities;

public class Admin extends User {

    public Admin(String userId, String username, String email,String password) {
        super(userId, username, email,password);
    }

    @Override
    public String toString() {
        return String.format("Admin: %s", super.toString());
    }
}

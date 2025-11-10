package com.tns.onlineshopping.entities;

/**
 * Base user class for the online shopping app.
 * Single canonical definition — ensure no other User class exists in the same package.
 */
public abstract class User {
    private final String userId;
    private final String username;
    private final String email;
    private final String password; // simple demo password (plaintext only for demo)

    public User(String userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return String.format("%s (%s)", username, email);
    }
}

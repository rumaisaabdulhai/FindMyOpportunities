package com.afg.helpout.Model;

public class User {

    private String username;
    private String email;
    private String password;

    /**
     * Constructs a user with an email and password. TODO: ADD USERNAME?
     * @param username the username of the user.
     * @param email the user's email.
     * @param password the user's password.
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the user's username.
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the user's email.
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user's email.
     * @param email the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get's the user's password.
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

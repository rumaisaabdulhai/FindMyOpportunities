package com.afg.helpout;

import java.util.ArrayList;

/**
 * The User Class
 *
 * Models a Firebase User.
 * The name, username, ID, and favorites
 * are stored in a User object.
 */
public class User {

    // Instance variables
    private String name;
    private String username;
    private String email;
    private String ID;
    private ArrayList<String> favorites;

    /**
     * Gets the name of the user.
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name The user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the username of the user
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username The user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email of the user.
     * @return The user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * @param email The user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the ID of the user.
     * @return
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the user.
     * @param ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the favorites ArrayList.
     * @return The favorites ArrayList
     */
    public ArrayList<String> getFavorites() {
        return favorites;
    }

    /**
     * Sets the favorite ArrayList.
     * @param favorites The favorites ArrayList
     */
    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    /**
     * Master Constructor for User.
     *
     * @param name The name of the user
     * @param username The username of the user
     * @param email The email of the user
     * @param id The ID of the user
     * @param favorites The array-list of favorite opportunities' IDs
     */
    public User(String name, String username, String email, String id, ArrayList<String> favorites) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.ID = id;
        this.favorites = favorites;
    }

    /**
     * Default Constructor for User.
     */
    public User() {
        this.name = "name";
        this.username = "username";
        this.email = "email";
        this.ID = "id";
        this.favorites = new ArrayList<>();
    }

}
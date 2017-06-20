package com.liang.databinding;

/**
 * Created by ichongliang on 9/15/16.
 */

public class User {
    private String firstName = "firstName";
    private String lastName = "lastName";
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
}

package com.example.aisentrytest.Controller;


public class UserQuery {
    public String userQuery;

    public UserQuery(String userQuery) {
        this.userQuery = userQuery;
    }

    public String getUserQuery() {
        return userQuery;
    }

    // No setters are provided as we only want to get the userQuery and not modify it.
}




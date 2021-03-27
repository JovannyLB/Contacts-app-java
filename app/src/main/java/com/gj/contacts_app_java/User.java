package com.gj.contacts_app_java;

import android.util.Log;

import java.util.Set;

public class User {
    private String username, password;

    // Gets an instance, if one does not exist, creates one
    private static User instance = null;

    public static User GetInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    // Gets the string set of the accounts info and sets it, on a specific instance, like a constructor would
    public void FeedInstance(Set<String> accountInfoSet) {
        String[] accountInfo = accountInfoSet.toArray(new String[accountInfoSet.size()]);

        GetInstance().setUsername(accountInfo[0]);
        GetInstance().setPassword(accountInfo[1]);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

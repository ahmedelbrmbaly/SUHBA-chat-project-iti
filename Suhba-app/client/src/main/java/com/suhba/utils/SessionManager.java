package com.suhba.utils;

import com.suhba.database.entities.User;

public class SessionManager {

    private static User loggedInUser;

    public static void setUser(User user) {
        loggedInUser = user;
    }

    public static User getUser() {
        return loggedInUser;
    }

    public static long getUserId() {
        return (loggedInUser != null) ? loggedInUser.getUserId() : -1;
    }

    public static void clearSession() {
        loggedInUser = null;
    }
}

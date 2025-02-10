package com.suhba.utils;

import com.suhba.database.entities.Admin;

public class SessionManager {
    private static Admin loggedInAdmin;

    public static void setAdmin(Admin admin) {
        loggedInAdmin = admin;
    }

    public static Admin getAdmin() {
        return loggedInAdmin;
    }

    public static Long getAdminId() {
        return loggedInAdmin != null ? loggedInAdmin.getAdminId() : null;
    }

    public static void clearSession() {
        loggedInAdmin = null;
    }
}

package com.suhba;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

import com.suhba.daos.DatabaseConnection;
import com.suhba.daos.implementation.ChatDAOImpl;

import com.suhba.daos.implementation.AdminDAOImpl;

import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.daos.interfaces.MessageDAO;
import com.suhba.database.entities.Admin;
import com.suhba.database.entities.Chat;

import com.suhba.database.entities.Group;

import com.suhba.database.entities.Admin;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ChatType;
import com.suhba.database.enums.MessageStatus;

public class Main {

    public static void main(String[] args) {
        AdminDAOImpl adminDAO = new AdminDAOImpl();

        Admin admin;
        admin = adminDAO.getAdminByEmail("ahmde@example.com");
        adminDAO.deleteAdmin(admin.getAdminId());

        System.out.println(adminDAO.getAllAdmins());




    }
}

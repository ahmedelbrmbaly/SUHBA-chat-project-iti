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
import com.suhba.services.server.implementations.ServerServiceImpl;
public class Main {

    public static void main(String[] args) {
        ServerServiceImpl serverService = new ServerServiceImpl();
        Admin admin = new Admin("ahmed", "a@a.com", "123", true);
        System.out.println(serverService.getCountryStatistics());
        System.out.println(serverService.getGenderStatistics());
        System.out.println(  serverService.getUserStatus());








    }
}

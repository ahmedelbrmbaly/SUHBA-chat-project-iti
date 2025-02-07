package com.suhba;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

import com.suhba.daos.DatabaseConnection;
import com.suhba.daos.implementation.ChatDAOImpl;

import com.suhba.daos.implementation.GroupDaoImpl;

import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.daos.interfaces.MessageDAO;
import com.suhba.database.entities.Chat;

import com.suhba.database.entities.Group;

import com.suhba.database.entities.User;
import com.suhba.exceptions.*;
import com.suhba.services.client.implementation.UserAuthServiceImpl;

public class Main {

    public static void main(String[] args) throws InvalidPhoneException, RepeatedPhoneException, InvalidPasswordException, InvalidEmailException, RepeatedEmailException, NoSuchAlgorithmException {
        UserAuthServiceImpl myObj = new UserAuthServiceImpl();
        User user = new User();

        myObj.signup(user);
        /*GroupDaoImpl groupDao = null;

        try {
            groupDao = new GroupDaoImpl(DatabaseConnection.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // System.out.println(groupDao.createGroup(new Group("NE"), 1));
        System.out.println(groupDao.getAllGroups());*/


    }
}

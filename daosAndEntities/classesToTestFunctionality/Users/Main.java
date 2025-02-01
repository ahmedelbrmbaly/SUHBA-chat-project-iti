package org.example;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserDAOImpl myObj = new UserDAOImpl();
        User myUser = new User();

        // addNewUser
//        myUser.setPhone("1212534");
//        myUser.setDisplayName("Aya");
//        myUser.setUserEmail("aya@gmail.com");
//        myUser.setPicture(null);
//        myUser.setPassword("1235");
//        myUser.setGender("Female");
//        myUser.setCountry("Egypt");
//        myUser.setBirthday(LocalDate.parse("2002-02-02"));
//        myUser.setBio("ffjkfj vvjkjkf fgkjgkg g fgkg");
//        myUser.setUserStatus("Away");
//        boolean done = myObj.addNewUser(myUser);
//        System.out.println(done);

        // getUserById
//        myUser = myObj.getUserById(1);
//        System.out.println(myUser);

        // getUserByPhone
//        myUser = myObj.getUserByPhone("1212534");
//        System.out.println(myUser);

        // getUserStatusById
//        String status = myObj.getUserStatusById(4);
//        System.out.println(status);

        // getUserDisplayNameById
//        String displayName = myObj.getUserDisplayNameById(1);
//        System.out.println(displayName);

        // getUsersByCountry
//        List<User> users = myObj.getUsersByCountry("Egypt");
//        System.out.println(users);

        // getUsersByStatus
//        List<User> users = myObj.getUsersByStatus("Offline");
//        System.out.println(users);

        // getUsersByGender
//        List<User> users = myObj.getUsersByGender("Male");
//        System.out.println(users);

        // getAllUsers
//        List<User> users = myObj.getAllUsers();
//        System.out.println(users);

        // updateUser
//        myUser.setUserId(1);
//        myUser.setPhone("10");
//        myUser.setDisplayName("Aya Hathout");
//        myUser.setUserEmail("aya@gmail.com");
//        myUser.setPicture(null);
//        myUser.setPassword("1235");
//        myUser.setGender("Female");
//        myUser.setCountry("Egypt");
//        myUser.setBirthday(LocalDate.parse("2002-02-02"));
//        myUser.setBio("ffjkfj vvjkjkf fgkjgkg g fgkg");
//        myUser.setUserStatus("Away");
//        boolean done = myObj.updateUser(myUser);
//        System.out.println(done);

        // updateUserProfileById
//        boolean done = myObj.updateUserProfileById(3);
//        System.out.println(done);

        // deleteUserById
//        boolean done = myObj.deleteUserById(4);
//        System.out.println(done);
    }
}
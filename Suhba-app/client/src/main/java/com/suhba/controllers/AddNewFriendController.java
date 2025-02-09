package com.suhba.controllers;

import com.suhba.database.entities.Contact;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ContactStatus;
import com.suhba.exceptions.InvalidPhoneException;
import com.suhba.services.controllers.ContactsServices;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.rmi.RemoteException;

public class AddNewFriendController {

    @FXML
    private ImageView addFriend;

    @FXML
    private GridPane addNewFriendBox;

    @FXML
    private ImageView blockFriend;

    @FXML
    private ImageView imageFriend;

    @FXML
    private Label nameFriend;

    @FXML
    private Label phoneFriend;

    @FXML
    private ImageView rejectFriend;


    ContactsServices contactsServices;
    User friendUser = new User();
    User currentUser = new User();
    Contact contact = new Contact();


    @FXML
    void handleAddFriend(MouseEvent event) {
        boolean isRequestSuccessful = false;
        Contact contact = new Contact();
        contact.setUserId1(currentUser.getUserId());
        contact.setUserId2(friendUser.getUserId());
        contact.setContactStatus(ContactStatus.PENDING);
        try {
            isRequestSuccessful = contactsServices.sendFriendRequest(contact);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void handleBlockFriend(MouseEvent event) {
        boolean isRequestSuccessful = false;
        Contact contact = new Contact();
        contact.setUserId1(currentUser.getUserId());
        contact.setUserId2(friendUser.getUserId());
        contact.setContactStatus(ContactStatus.BLOCKED);
        try {
            isRequestSuccessful = contactsServices.(contact);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void handleRejectFriend(MouseEvent event) {
        boolean isRequestSuccessful = false;
        Contact contact = new Contact();
        contact.setUserId1(currentUser.getUserId());
        contact.setUserId2(friendUser.getUserId());
        contact.setContactStatus(ContactStatus.PE);
        try {
            isRequestSuccessful = contactsServices.sendFriendRequest(contact);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    // I think no need to implemnet the below
    @FXML
    void handleImageFriend(MouseEvent event) {

    }

    @FXML
    void handleNameFriend(MouseEvent event) {

    }

    @FXML
    void handlePhoneFriend(MouseEvent event) {

    }


    public void setCurrentUser(User user)
    {
        this.currentUser = user;
    }

    public void setFriendUser(User user)
    {
        this.friendUser = user;
    }

    public void setFriendContact(){
        this.contact.setUserId1(currentUser.getUserId());
        this.contact.setUserId2(friendUser.getUserId());
        this.contact.setContactStatus(ContactStatus.PENDING);
    }

    public void setBoxUserByUser(User user)
    {
        if (user.getPicture() == null) {
            imageFriend.setImage(new Image(getClass().getResourceAsStream("/images/defaultUser.png")));
        }
        nameFriend.setText(user.getDisplayName());
        phoneFriend.setText(user.getPhone());
        this.friendUser = user;
    }


    public void setBoxUserByPhone(String phone)
    {
        User user = null;

        try {
            user = contactsServices.getUserByPhone(phone);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (InvalidPhoneException e) {
            throw new RuntimeException(e);
        }

        if (user.getPicture() == null) {
            imageFriend.setImage(new Image(getClass().getResourceAsStream("/images/defaultUser.png")));
        }
        nameFriend.setText(user.getDisplayName());
        phoneFriend.setText(user.getPhone());
        this.friendUser = user;
    }


}

package com.suhba.controllers;

import com.suhba.database.entities.User;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class AddFriendGroupBoxController {

    @FXML CheckBox addCheckBox;

    @FXML
    private Label userName;

    @FXML
    private Label userPhone;

    private User user;

    public void setUser(User user) {
        this.user = user;
        userName.setText(user.getDisplayName());
        userPhone.setText(user.getPhone());
    }

    public boolean isSelected() {
        return addCheckBox.isSelected();
    }

    public User getUser() {
        return user;
    }

}

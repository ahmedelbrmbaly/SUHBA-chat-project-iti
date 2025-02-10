package com.suhba.controllers;

import com.suhba.services.controllers.AddNewFriendService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

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

    AddNewFriendService myServices = new AddNewFriendService();

    @FXML
    void handleAddFriend(MouseEvent event) {
        // myServices.sendFriendRequest(myServices.getCurUser().getUserId(), );
    }

    @FXML
    void handleImageFriend(MouseEvent event) {

    }

    @FXML
    void handleNameFriend(MouseEvent event) {

    }

    @FXML
    void handlePhoneFriend(MouseEvent event) {

    }

}

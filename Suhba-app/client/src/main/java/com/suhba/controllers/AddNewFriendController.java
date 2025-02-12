package com.suhba.controllers;

import com.suhba.services.controllers.AddNewFriendService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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

    AddNewFriendService myServices = new AddNewFriendService();

    public void setNewFriendData(String name, String phoneNumber) {
        //imageFriend = new ImageView(new Image(imageURL));
        nameFriend.setText(name);
        phoneFriend.setText(phoneNumber);
    }

    @FXML
    public boolean handleAddFriend(MouseEvent event) {
        System.out.println("In send a friend request to a person");
        Platform.runLater(() -> {
            Node sourceElement = (Node) event.getSource();
            Pane parentPane = (Pane) sourceElement.getParent().getParent();
            String phoneNumber = (String) parentPane.getUserData();
            if (phoneNumber != null) {
                try {
                    if (myServices.sendFriendRequest(phoneNumber)) {
                        FlowPane container = ClientAddContactScreenController.getContainer();
                        if (container != null)  container.getChildren().remove(parentPane);
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else System.out.println("Cannot determine which friend was clicked!");
        });
        return false;
    }

}

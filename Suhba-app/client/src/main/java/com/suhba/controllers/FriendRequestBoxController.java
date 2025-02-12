package com.suhba.controllers;

import com.suhba.database.entities.Contact;
import com.suhba.database.enums.ContactStatus;
import com.suhba.services.controllers.FriendRequestBoxService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class FriendRequestBoxController {

    @FXML
    private ImageView acceptFriend;

    @FXML
    private ImageView blockFriend;

    @FXML
    private GridPane friendRequest;

    @FXML
    private ImageView imageFriend;

    @FXML
    private Label nameFriend;

    @FXML
    private Label phoneFriend;

    @FXML
    private ImageView rejectFriend;

    FriendRequestBoxService myServices = new FriendRequestBoxService();

    public void setNewFriendData(String name, String phoneNumber) {
        //imageFriend = new ImageView(new Image(imageURL));
        nameFriend.setText(name);
        phoneFriend.setText(phoneNumber);
    }

    //If the user accepts the invitation
    @FXML
    void handleAcceptFriend(MouseEvent event) {
        System.out.println("You accepted the invitation");
        Platform.runLater(() -> {
            Node sourceElement = (Node) event.getSource();
            Pane parentPane = (Pane) sourceElement.getParent().getParent();
            String phoneNumber = (String) parentPane.getUserData();
            if (phoneNumber != null) {
                try {
                    if (myServices.acceptInvitation(phoneNumber)) {
                        FlowPane container = ClientRequestScreenController.getContainer();
                        if (container != null)  container.getChildren().remove(parentPane);
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else System.out.println("Cannot determine which friend was clicked!");
        });
    }

    @FXML
    void handleImageFriend(MouseEvent event) {

    }

    @FXML
    void handleRejectFriend(MouseEvent event) {
        System.out.println("You rejected the invitation");
        Platform.runLater(() -> {
            Node sourceElement = (Node) event.getSource();
            Pane parentPane = (Pane) sourceElement.getParent().getParent();
            String phoneNumber = (String) parentPane.getUserData();
            if (phoneNumber != null) {
                try {
                    if (myServices.rejectInvitation(phoneNumber)) {
                        FlowPane container = ClientRequestScreenController.getContainer();
                        if (container != null)  container.getChildren().remove(parentPane);
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else System.out.println("Cannot determine which friend was clicked!");
        });
    }

}

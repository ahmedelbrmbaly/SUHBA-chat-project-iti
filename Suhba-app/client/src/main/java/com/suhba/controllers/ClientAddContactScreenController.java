package com.suhba.controllers;

import com.suhba.services.controllers.ClientAddContactScreenService;
import com.suhba.utils.FXMLLoader;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.net.URL;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ClientAddContactScreenController implements Initializable {
    @FXML
    private BorderPane addNewFriendScreen;
    @FXML
    private TextField searchField;
    @FXML
    private Label AddNewLAbel;
    @FXML
    private FlowPane friendContainer;
    @FXML
    private GridPane newFriend_1;

    ClientAddContactScreenService myServices = new ClientAddContactScreenService();
    Map<Integer, String> recipients;
    int indx;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // recipients.clear();
        recipients = new HashMap<>();
        indx = 0;
        friendContainer.setOrientation(Orientation.VERTICAL);
    }

    public void sendRequest(MouseEvent mouseEvent) throws RemoteException {
        System.out.println("In send a friend request to a person");
        Platform.runLater(() -> {
            try {
                if (myServices.sendFriendRequest(recipients.get(2))) {
                    recipients.remove(2);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void handleAddToListBtn(ActionEvent actionEvent) throws IOException {
        System.out.println("In add to listview");
        if (searchField.getText().length() > 0) {
            recipients.put(indx++, searchField.getText());
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane curView = fxmlLoader.getPane("AddNewFriendBox");
            Label label = new Label(searchField.getText());
            friendContainer.getChildren().add(curView);
        }
    }

    public void handleSendRequestBtn(ActionEvent actionEvent) throws IOException {
        friendContainer.getChildren().clear();
        System.out.println("In send a friend request to many people");
        boolean ok = true;
        for (Map.Entry<Integer, String> recipient: recipients.entrySet()){
            if (!myServices.sendFriendRequest(recipient.getValue())) {
                ok = false;
                break;
            }
        }
        if (ok){
            for (Map.Entry<Integer, String> recipient: recipients.entrySet()) {
                System.out.print(recipient.getKey() + ":");
                System.out.println(recipient.getValue());
            }
            recipients.clear();
        }
    }
}

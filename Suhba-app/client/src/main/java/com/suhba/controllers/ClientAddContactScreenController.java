package com.suhba.controllers;

import com.suhba.services.controllers.ClientAddContactScreenService;
import com.suhba.utils.FXMLHelper;
import javafx.application.Platform;
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


    //Lazy Initialization
    private static ClientAddContactScreenController instance;
    public ClientAddContactScreenController() {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (instance == null)  instance = this;

        recipients = new HashMap<>();
        indx = 0;
        friendContainer.setOrientation(Orientation.VERTICAL);
    }

    public static FlowPane getContainer() {
        if (instance != null) return instance.friendContainer;
        return null;
    }

    public void handleAddToListBtn(ActionEvent actionEvent) throws IOException {
        System.out.println("In add to listview");
        if (searchField.getText().length() > 0) {
            recipients.put(indx++, searchField.getText());

            FXMLHelper fxmlHelper = new FXMLHelper();
            Pane curView = fxmlHelper.getPane("AddNewFriendBox");

            AddNewFriendController controller = (AddNewFriendController) fxmlHelper.getController();
            if (controller != null) {
                String friendName = myServices.getDisplayNameByPhoneNumber(searchField.getText());
                if (friendName == null) {
                    myServices.showErrorAlert("There is no user with this phone number");
                    searchField.clear();
                    return;
                }
                String phoneNumber = searchField.getText();
                if (myServices.isMe(phoneNumber)) {
                    myServices.showErrorAlert("You are not allowed to send a friend request to yourself!");
                    searchField.clear();
                    return;
                }
                controller.setNewFriendData(friendName, phoneNumber);

                curView.setUserData(phoneNumber);
            } else {
                System.out.println("Error: Controller is null!");
            }

            friendContainer.getChildren().add(curView);
            searchField.clear();
        }
    }

    public void handleSendRequestBtn(ActionEvent actionEvent) throws IOException {
        friendContainer.getChildren().clear();
        System.out.println("In send a friend request to many people");
        boolean ok = true;
        for (Map.Entry<Integer, String> recipient: recipients.entrySet()){
            List<String> recipientList = new ArrayList<>();
            recipientList.add(recipient.getValue());
            if (!myServices.sendFriendRequest(recipientList)) {
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

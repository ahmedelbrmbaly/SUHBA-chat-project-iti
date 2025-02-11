package com.suhba.controllers;

import com.suhba.database.entities.User;
import com.suhba.services.controllers.ClientRequestScreenService;
import com.suhba.utils.FXMLHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientRequestScreenController implements Initializable {

    @FXML
    private FlowPane pendingRequestsContainer;

    ClientRequestScreenService myServices = new ClientRequestScreenService();

    List<User> pendingRequests;

    private static ClientRequestScreenController instance;
    public ClientRequestScreenController() {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (instance == null)  instance = this;

        pendingRequests = new ArrayList<>();
        try {
            pendingRequests = myServices.showPendingRequests();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Show
        System.out.println(pendingRequests);

        // Show the list of all pending requests
        Platform.runLater( () -> {
            for (User user: pendingRequests) {
                FXMLHelper fxmlHelper = new FXMLHelper();
                Pane curView = null;
                try {
                    curView = fxmlHelper.getPane("FriendRequestBox");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                FriendRequestBoxController controller = (FriendRequestBoxController) fxmlHelper.getController();
                if (controller != null) {
                    String friendName = user.getDisplayName();
                    String phoneNumber = user.getPhone();
                    controller.setNewFriendData(friendName, phoneNumber);
                    curView.setUserData(phoneNumber);
                } else {
                    System.out.println("Error: Controller is null!");
                }
                pendingRequestsContainer.getChildren().add(curView);
            }
        });
    }

    public static FlowPane getContainer() {
        if (instance != null) return instance.pendingRequestsContainer;
        return null;
    }
}
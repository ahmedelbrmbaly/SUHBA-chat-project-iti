package com.suhba.controllers;

import com.suhba.database.entities.User;
import com.suhba.services.controllers.ClientContactScreenService;
import com.suhba.utils.FXMLHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientContactScreenController implements Initializable {

    @FXML
    private Label GroupsLabel;

    @FXML
    private Label RequestsLabel;

    @FXML
    private Button addNewBtn;

    @FXML
    private VBox chatBoxBar;

    @FXML
    private ImageView chatIconView;

    @FXML
    private Label chatLabel;

    @FXML
    private VBox chatbotBoxBar;

    @FXML
    private ImageView chatbotIconView;

    @FXML
    private Label chatbotLabel;

    @FXML
    private VBox contactsBoxBar;

    @FXML
    private ImageView contactsIconView;

    @FXML
    private Label contactsLabel;

    @FXML
    private Label friendBio;

    @FXML
    private Label friendEmail;

    @FXML
    private ImageView friendImage;

    @FXML
    private Label friendName;

    @FXML
    private Label friendPhone;

    @FXML
    private Circle friendStatus11112;

    @FXML
    private AnchorPane friend_1;

    @FXML
    private Label friendlastSeen_1;

    @FXML
    private VBox groupBoxBar;

    @FXML
    private ImageView groupIconView;

    @FXML
    private VBox logoutBoxBar;

    @FXML
    private ImageView logoutIconView;

    @FXML
    private Label logoutLabel;

    @FXML
    private VBox requestBoxBar;

    @FXML
    private ImageView requestIconView;

    @FXML
    private TextField searchField;

    @FXML
    private VBox settingBoxBar;

    @FXML
    private ImageView settingIconView;

    @FXML
    private Label settingLabel;

    @FXML
    private Label shawingLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private ImageView userProfilePic;

    @FXML
    private FlowPane friendsContainer;

    ClientContactScreenService myServices = new ClientContactScreenService();

    List<User> friends;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        friendsContainer.setOrientation(Orientation.HORIZONTAL);

        friends = new ArrayList<>();
        try {
            friends = myServices.showFriends();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Show
        System.out.println(friends);

        // Show the list of all friends
        Platform.runLater( () -> {
            for (User user: friends) {
                FXMLHelper fxmlHelper = new FXMLHelper();
                Pane curView = null;
                try {
                    curView = fxmlHelper.getPane("FriendContactInfo");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                FriendContactInfoController controller = (FriendContactInfoController) fxmlHelper.getController();
                if (controller != null) {
                    String friendName = user.getDisplayName();
                    String phoneNumber = user.getPhone();
                    String bio = user.getBio();
                    String email = user.getUserEmail();
                    String status = user.getUserStatus().name();
                    Color circleColor;
                    switch (status) {
                        case "Offline":
                            circleColor = Color.RED;
                            break;
                        case "Available":
                            circleColor = Color.GREEN;
                            break;
                        case "Busy":
                            circleColor = Color.GREEN;
                    }
                    controller.setNewFriendData(Color.BLUE, friendName, bio, email, phoneNumber);
                    curView.setUserData(phoneNumber);
                } else {
                    System.out.println("Error: Controller is null!");
                }
                friendsContainer.getChildren().add(curView);
            }
        });
    }

    @FXML
    void handleAddNewFriend(ActionEvent event) {
        myServices.moveToNextPage(event, "ClientAddContactScreen.fxml");
    }

    @FXML
    void handleSearch(ActionEvent event) {

    }

}


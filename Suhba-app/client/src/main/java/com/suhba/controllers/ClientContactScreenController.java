package com.suhba.controllers;

import com.suhba.database.entities.User;
import com.suhba.database.enums.UserStatus;
import com.suhba.services.UserService;
import com.suhba.services.controllers.ChatScreenService;
import com.suhba.services.controllers.ClientContactScreenService;
import com.suhba.utils.FXMLHelper;
import com.suhba.utils.LoadingFXML;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
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

    
    @FXML
    private Button viewReqBtn;

    ClientContactScreenService myServices = new ClientContactScreenService();

    List<User> friends;

    UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        friendsContainer.setOrientation(Orientation.HORIZONTAL);
        setUserInfo();
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
                    UserStatus status = user.getUserStatus();
                    Color circleColor;
                    switch (status) {
                        case UserStatus.Available:
                            circleColor = Color.GREEN;
                            break;
                        case UserStatus.Offline:
                            circleColor = Color.GRAY;
                            break;
                        case UserStatus.Busy:
                            circleColor = Color.RED;
                            break;
                        case UserStatus.Away:
                            circleColor = Color.YELLOW;
                            break;
                        default:
                            circleColor = Color.WHITE;
                            break;
                    }

                    byte[] userPhoto = null;
                    try {
                        userPhoto = myServices.getUserByPhone(phoneNumber).getPicture();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    Image curUserPic = new Image(getClass().getResourceAsStream("/images/defaultUser.png"));
                    if (userPhoto != null && userPhoto.length > 0) {
                        curUserPic = new Image(new ByteArrayInputStream(userPhoto));
                    }

                    controller.setNewFriendData(curUserPic, circleColor, friendName, bio, email, phoneNumber);
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
        // myServices.moveToNextPage(event, "ClientAddContactScreen.fxml");
        Node currentNode = (Node)event.getSource();
        Stage owner = (Stage)currentNode.getScene().getWindow();
        // load the popup content
        URL fxmlURL = getClass().getResource("/com/suhba/ClientAddContactScreen.fxml");
        LoadingFXML.showPopupWithIdAddFriend(owner, fxmlURL,500,500, 1);
    }

    @FXML
    void handleSearch(ActionEvent event) {

    }

    @FXML
    void goToChat(MouseEvent event) {
        myServices.moveToNextPage(event, "ClientChatScreen.fxml");
    }

    @FXML
    void goToGroups(MouseEvent event) {
        LoadingFXML.moveToNextPage(event, "ClientGroupScreen.fxml");
    }

    @FXML
    void goToSettings(MouseEvent event) {
        myServices.moveToNextPage(event, "ProfileSettingsScreen.fxml");
    }

    @FXML
    void handleViewReq(ActionEvent event) {
        Node currentNode = (Node)event.getSource();
        Stage owner = (Stage)currentNode.getScene().getWindow();
        // load the popup content
        URL fxmlURL = getClass().getResource("/com/suhba/ClientRequestScreen.fxml");
        LoadingFXML.showPopupWithIdReqFriend(owner, fxmlURL,500,500, 1);
    }


    public void setUserInfo() {
        try {
            userService = new UserService();
            User currentUser = userService.getUserInfoById(myServices.getCurUser().getUserId());

            if (currentUser != null) {
                byte[] userPhoto = currentUser.getPicture();
                if (userPhoto != null && userPhoto.length > 0) {
                    Image image = new Image(new ByteArrayInputStream(userPhoto));
                    userProfilePic.setImage(image);
                } else {
                    userProfilePic.setImage(new Image(getClass().getResourceAsStream("/images/defaultUser.png")));
                }
            }

            userNameLabel.setText(currentUser.getDisplayName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void handleLogout(MouseEvent mouseEvent) throws IOException {
        new ChatScreenService().unregister(myServices.getCurUser().getUserId());
        myServices.logoutService();
        LoadingFXML.moveToNextPage(mouseEvent, "signInPage1.fxml");
    }
}


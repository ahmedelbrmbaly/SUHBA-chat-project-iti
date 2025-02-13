package com.suhba.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.database.enums.MessageStatus;
import com.suhba.services.MessagingService;
import com.suhba.services.UserService;
import com.suhba.services.controllers.ChatScreenService;
import com.suhba.services.controllers.GroupScreenService;
import com.suhba.utils.LoadingFXML;
import com.suhba.views.cells.ChatGroupCell;
import com.suhba.views.cells.GroupMessageBubble;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ClientGroupScreenController implements Initializable {

    @FXML
    private Label GroupsLabel;

    @FXML
    private Button addNewGroup;

    @FXML
    private ImageView attachBtn;

    @FXML
    private ImageView cameraBtn;

    @FXML
    private VBox chatBoxBar;

    @FXML
    private ImageView chatIconView;

    @FXML
    private Label chatLabel;

    @FXML
    private GridPane chatMessageFooter;

    @FXML
    private GridPane chatMessageHeader;

    @FXML
    private Label chatNameLabel;

    @FXML
    private ImageView chatPicture;

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
    private ImageView emojiBtn;

    @FXML
    private VBox groupBoxBar;

    @FXML
    private ImageView groupIconView;

    @FXML
    private Label groupMembersLabel;

    @FXML
    private VBox logoutBoxBar;

    @FXML
    private ImageView logoutIconView;

    @FXML
    private Label logoutLabel;

    @FXML
    private TextField messageField;

    @FXML
    private ImageView micBtn;

    @FXML
    private ImageView phoneBtn;

    @FXML
    private TextField searchField;

    @FXML
    private VBox settingBoxBar;

    @FXML
    private ImageView settingIconView;

    @FXML
    private Label settingLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private ImageView userProfilePic;

    @FXML
    private ImageView videoBtn;

    @FXML
    private ListView<Message> messagesArea;
    private ObservableList<Message> listOfMessages;

    @FXML
    private ListView<Group> chatsListView;
    ObservableMap<Group, Message> allChats;

    private long currentUserId;

    long currentChatId = -1;

    Group currentGroupInChatWith;

    GroupScreenService groupService;

    User currentUser;

    UserService userService;

    MessagingService messagingService;

    String groupMembers;

    private static ClientGroupScreenController instance;

    public ClientGroupScreenController() {
        if (instance == null) {
            instance = this;
        }
    }

    public static ClientGroupScreenController getInstance() {
        return instance;
    }

    @FXML
    void goToChats(MouseEvent event) {
        try {
            // Load Groups FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/suhba/ClientChatScreen.fxml"));
            Parent root = loader.load();

            // Get the GroupsController
            // ChatScreenController groupsController = loader.getController();
            // groupsController.setCurrentUserId(currentUserId); // Pass user ID

            // Switch Scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId = currentUserId;
        System.out.println("from setter");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            // 1- Load Services + SetUp
            userService = new UserService();
            messagingService = new MessagingService();

            System.out.println("userId = " + currentUserId);
            chatMessageHeader.setVisible(false);
            chatMessageFooter.setVisible(false);
            messagesArea.setVisible(false);
            setUserInfo();

            // 2- Load Groups
            groupService = new GroupScreenService(this);
            currentUserId = groupService.getCurUser().getUserId();
            groupService.registerToReceive(currentUserId);
            allChats = groupService.loadUserGroups(currentUserId);

            // 3- Show In GUI
            ObservableList<Group> userList = FXCollections.observableArrayList(allChats.keySet());
            Platform.runLater(() -> {
                allChats.addListener((MapChangeListener<Group, Message>) change -> {
                    if (change.wasAdded() || change.wasRemoved()) {
                        // updateChatList();
                    }
                });
                chatsListView.setItems(userList);
                chatsListView.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

                chatsListView.setCellFactory(ListView -> new ChatGroupCell(allChats));
            });

            // Load Messages
            if (listOfMessages == null) {
                listOfMessages = FXCollections.observableArrayList();
            }
            messagesArea.setOnScroll(event -> {
                if (event.getDeltaY() != 0) {
                    event.consume();
                }
            });
            Platform.runLater(() -> {
                messagesArea.setItems(listOfMessages);
                messagesArea.setSelectionModel(null);
                listOfMessages.addListener((ListChangeListener<Message>) change -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            System.out.println("New messages added: " + change.getAddedSubList());
                            Platform.runLater(() -> {
                                messagesArea.scrollTo(listOfMessages.size() - 1);
                            });
                        }
                        if (change.wasRemoved()) {
                            System.out.println("Messages removed: " + change.getRemoved());
                        }
                    }
                });
            });

            // Handle When User Select specific Group Chat
            chatsListView.setOnMouseClicked(event -> {
                Group selectedGroup = chatsListView.getSelectionModel().getSelectedItem();
                if (selectedGroup != null) {
                    Message lMessage = allChats.get(selectedGroup);
                    if (lMessage != null) {
                        currentChatId = lMessage.getChatId();
                        System.out.println(currentChatId);
                        try {
                            // get Selected Group and its info
                            currentGroupInChatWith = groupService.getSelectedGroupInfo(currentChatId);
                            groupMembers = groupService.getGroupMembers(currentGroupInChatWith.getGroupId());
                            showCurrentGroupInChatInfo();
                            // load Messages in this Group
                            listOfMessages.clear();
                            listOfMessages.setAll(messagingService.getSelectedChatMessages(currentChatId));
                            Platform.runLater(() -> messagesArea.setItems(listOfMessages));
                            showCurrentChatMessages();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Getting selected user exception");
                        }
                        // Invoke Method to load all Messages in this chat
                    }
                }
            });

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String msg = messageField.getText();
                try {
                    Message m = new Message(currentUserId, currentChatId, msg, MessageStatus.Sent, null);
                    System.out.println("before sending..." + m);
                    m = messagingService.sendMessageToUser(m);
                    System.out.println("after sending... " + m);
                    // Platform.runLater(() -> {
                    listOfMessages.add(m);
                    // });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                messageField.clear();
            }
        });

    }

    public boolean receiveNewMessage(Message msg) {
        Platform.runLater(() -> {
            if (listOfMessages == null) {
                listOfMessages = FXCollections.observableArrayList();
                messagesArea.setItems(listOfMessages);
            }
        });
        Platform.runLater(() -> {
            if (!listOfMessages.contains(msg)) {
                listOfMessages.add(msg);
                messagesArea.scrollTo(listOfMessages.size() - 1);
            }
        });
        // listOfMessages.add(msg);
        // messagesArea.scrollTo(listOfMessages.size() - 1);
        System.out.println("Received new message: " + msg);

        return true;
    }

    private void showCurrentGroupInChatInfo() {
        if (!chatMessageHeader.isVisible() && currentGroupInChatWith != null && !chatMessageFooter.isVisible()
                && !messagesArea.isVisible()) {
            chatMessageHeader.setVisible(true);
            chatMessageFooter.setVisible(true);
            messagesArea.setVisible(true);
        }
        if (currentGroupInChatWith != null) {
            Platform.runLater(() -> {
                if (currentGroupInChatWith.getGroupPhoto() == null) {
                    chatPicture.setImage(new Image(getClass().getResourceAsStream("/images/defaultGroup.png")));
                } else {
                    // chatPicture.setImage((Image) currentGroupInChatWith.getGroupPhoto());
                }
                chatNameLabel.setText(currentGroupInChatWith.getGroupName());
                groupMembersLabel.setText(groupMembers);
                Circle circle = new Circle(30, 30, 30);
                chatPicture.setClip(circle);
            });

        }
    }

    public void setUserInfo() {
        try {
            User currentUser = userService.getUserInfoById(new GroupScreenService().getCurUser().getUserId());
            byte[] userPhoto = currentUser.getPicture();

            if (userPhoto != null && userPhoto.length > 0) {
                Image image = new Image(new ByteArrayInputStream(userPhoto));
                userProfilePic.setImage(image);
            } else {
                userProfilePic.setImage(new Image(getClass().getResourceAsStream("/images/defaultUser.png")));
            }

            userNameLabel.setText(currentUser.getDisplayName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentChatMessages() {
        Platform.runLater(() -> {
            messagesArea.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {

                @Override
                public ListCell<Message> call(ListView<Message> param) {
                    return new GroupMessageBubble(currentUserId);
                }

            });
        });

    }

    @FXML
    void handleAddNewGroup(ActionEvent event) {
        Node currentNode = (Node) event.getSource();
        Stage owner = (Stage) currentNode.getScene().getWindow();
        // load the popup content
        URL fxmlURL = getClass().getResource("/com/suhba/AddNewGroup.fxml");
        LoadingFXML.showPopupWithId(owner, fxmlURL, 500, 500, currentUserId);
    }

    @FXML
    void goToContacts(MouseEvent event) {
        LoadingFXML.moveToNextPage(event, "ClientContactScreen.fxml");
    }

    @FXML
    void goToSettings(MouseEvent event) {
        LoadingFXML.moveToNextPage(event, "ProfileSettingsScreen.fxml");
    }

    @FXML
    void goToLogout(MouseEvent event) {
        ChatScreenService chatScreenService = new ChatScreenService();
        try {
            chatScreenService.unregister(currentUserId);
            chatScreenService.logoutService();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LoadingFXML.moveToNextPage(event, "signInPage1.fxml");
    }

    @FXML
    void handleAttachBtn(MouseEvent event) {

    }

    @FXML
    void handleCameraBtn(MouseEvent event) {

    }

    @FXML
    void handleEmojiBtn(MouseEvent event) {

    }

    @FXML
    void handleMicBtn(MouseEvent event) {

    }

    @FXML
    void handlePhoneBtn(MouseEvent event) {

    }

    @FXML
    void handleSearch(ActionEvent event) {

    }

    @FXML
    void handleSendMessage(ActionEvent event) {

    }

    @FXML
    void handleVideoBtn(MouseEvent event) {

    }
}

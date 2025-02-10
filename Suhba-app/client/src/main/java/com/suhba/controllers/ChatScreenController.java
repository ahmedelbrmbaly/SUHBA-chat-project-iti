package com.suhba.controllers;

import java.io.IOException;

// import com.suhba.models.*;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.database.enums.MessageStatus;
import com.suhba.services.controllers.ChatScreenService;
import com.suhba.views.cells.ChatUserCell;
import com.suhba.views.cells.MessageBubbleCell;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

public class ChatScreenController implements Initializable {
    @FXML
    private Label GroupsLabel;

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
    private VBox chatsVBox;

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
    private VBox logoutBoxBar;

    @FXML
    private ImageView logoutIconView;

    @FXML
    private Label logoutLabel;

    @FXML
    private ImageView micBtn;

    @FXML
    private ImageView phoneBtn;

    @FXML
    private TextField searchTextField;

    @FXML
    private VBox settingBoxBar;

    @FXML
    private ImageView settingIconView;

    @FXML
    private Label settingLabel;

    @FXML
    private ImageView showRequestsBtn;

    @FXML
    private Circle userChatStatusCircle;

    @FXML
    private Label userChatStatusLabel;

    @FXML
    private TextField userMessageTextField;

    @FXML
    private Label userNameLabel;

    @FXML
    private ImageView userProfilePic;

    @FXML
    private ImageView videoBtn;

    @FXML
    private GridPane chatMessageHeader;

    @FXML
    private GridPane chatMessageFooter;

    @FXML
    private ListView<Message> messagesArea;

    private ObservableList<Message> listOfMessages;

    @FXML
    private ListView<User> chatsListView;

    ObservableMap<User, Message> allChats;

    ChatScreenService chatScreenService;

    long currentChatId = -1;

    long currentUserId = 1;

    User currentUserInChatWith;

    private static ChatScreenController instance;

    public ChatScreenController() {
        if (instance == null) { 
            instance = this; 
        }
    }

    public static ChatScreenController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {
            chatScreenService = new ChatScreenService(this);
            System.out.println("controler= " + this);
            chatMessageHeader.setVisible(false);
            chatMessageFooter.setVisible(false);
            chatScreenService.registerToReceive(currentUserId);
            allChats = chatScreenService.loadUserChats(currentUserId);
            ObservableList<User> userList = FXCollections.observableArrayList(allChats.keySet());

            if (listOfMessages == null) {
                listOfMessages = FXCollections.observableArrayList();
            }
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

            allChats.addListener((MapChangeListener<User, Message>) change -> {
                if (change.wasAdded() || change.wasRemoved()) {
                    updateChatList();
                }

            });

            // listOfMessages.addListener(null);

            chatsListView.setItems(userList);
            chatsListView.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

            Platform.runLater(() -> {
                chatsListView.setCellFactory(ListView -> new ChatUserCell(allChats));
            });

            chatsListView.setOnMouseClicked(event -> {
                User selectedUser = chatsListView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    Message lMessage = allChats.get(selectedUser);
                    if (lMessage != null) {
                        currentChatId = lMessage.getChatId();
                        System.out.println(currentChatId);
                        try {
                            currentUserInChatWith = chatScreenService.getSelectedUserInfo(currentChatId, currentUserId);
                            showCurrentUserInChatInfo();
                            listOfMessages = FXCollections.observableList(chatScreenService
                                    .getSelectedUserMessages(currentChatId));
                            messagesArea.setItems(listOfMessages);
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

        // Handle Chat View :
        userMessageTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String msg = userMessageTextField.getText();
                // Create Message Object
                // Add Message Object to the Observable List
                // Add Message after inserting in table :
                // Add this chat to be at first
                // try {

                try {
                    Message m = new Message(currentUserId, currentChatId, msg, MessageStatus.Sent, null);
                    System.out.println("before sending..." + m);
                    m = chatScreenService.sendMessageToUser(m);
                    System.out.println("after sending... " + m);
                    // Platform.runLater(() -> {
                    listOfMessages.add(m);
                    // });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // if (allChats.containsKey(currentUserInChatWith)) {
                // allChats.replace(currentUserInChatWith, m);
                // } else {
                // allChats.put(currentUserInChatWith, m);
                // }
                // updateChatList();
                // } catch (Exception e) {
                // System.out.println("Message Not Delivered");
                // e.printStackTrace();
                // }
                // System.out.println("New Message: " + m);
                userMessageTextField.clear();
            }
        });

    }

    private void updateChatList() {
        ObservableList<User> userList = FXCollections.observableArrayList(allChats.keySet());
        FXCollections.sort(userList, (u1, u2) -> {
            Message m1 = allChats.get(u1);
            Message m2 = allChats.get(u2);
            return m2.getTimeStamp().compareTo(m1.getTimeStamp());
        });
        chatsListView.setItems(userList);
    }

    private void showCurrentChatMessages() {
        messagesArea.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {

            @Override
            public ListCell<Message> call(ListView<Message> param) {
                return new MessageBubbleCell(currentUserId);
            }

        });
    }

    private void showCurrentUserInChatInfo() {
        if (!chatMessageHeader.isVisible() && currentUserInChatWith != null && !chatMessageFooter.isVisible()) {
            chatMessageHeader.setVisible(true);
            chatMessageFooter.setVisible(true);
        }
        if (currentUserInChatWith != null) {
            if (currentUserInChatWith.getPicture() == null) {
                chatPicture.setImage(new Image(getClass().getResourceAsStream("/images/defaultUser.png")));
            } else {
                chatPicture.setImage((Image) currentUserInChatWith.getPicture());
            }
            chatNameLabel.setText(currentUserInChatWith.getDisplayName());
            userChatStatusLabel.setText(currentUserInChatWith.getUserStatus().name());
            Circle circle = new Circle(30, 30, 30);
            chatPicture.setClip(circle);
        }
    }

    public void goToScene(String fxmlName) {
        // FXMLLoader loader = new FXMLLoader(App.class.getResource(
        // "/gov/iti/jets/ChatUI.fxml"));
        // Parent root = loader.load();
        // ClientController ch = loader.getController();
        // ch.setName(getName());
        // Stage stage = (Stage) nameTextField.getScene().getWindow();
        // stage.setScene(new Scene(root));
        // stage.show();
    }

    public boolean receiveNewMessage(Message msg) {
        Platform.runLater(() -> {
            if (listOfMessages == null) {
                listOfMessages = FXCollections.observableArrayList();
                messagesArea.setItems(listOfMessages);
            }
            listOfMessages.add(msg);
            messagesArea.scrollTo(listOfMessages.size() - 1);
            System.out.println("Received new message: " + msg);
        });
        return true;
    }

}

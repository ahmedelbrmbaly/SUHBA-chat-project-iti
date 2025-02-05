package com.suhba.controllers;

import com.suhba.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Callback;


public class ChatScreenController implements Initializable{
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
    private ListView<Message> messagesArea;

    private ObservableList<Message> listOfMessages; 

    @FXML
    private ListView<Chat> chatsListView;

    private ObservableList<Chat> listOfChats;

    private Chat currenChat;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        //Test MessagesList:
        listOfMessages = FXCollections.observableArrayList();
        listOfChats = FXCollections.observableArrayList();

        
        User user1 = new User("ghaidaa");
        User user2 = new User("yousef");
        Chat chat1 = new Chat("chat-1", user2);
        Message received= new Message(user2,"Test Receive");
        Message received2= new Message(user2,"Test Receive 2");
        chat1.getMessages().add(received);
        chat1.getMessages().add(received2);
        listOfChats.add(chat1);
        listOfMessages.add(received);
        listOfMessages.add(received2);

        chatsListView.setItems(listOfChats);
        chatsListView.setStyle("-fx-background-color:  #F9F7F7;");

        messagesArea.setItems(listOfMessages);
        messagesArea.setOrientation(Orientation.VERTICAL);
        messagesArea.setStyle("-fx-background-color: transparent;");
        messagesArea.setSelectionModel(null);
        

        

        // Handle Chat View :
        userMessageTextField.setOnKeyPressed(event ->{
            if (event.getCode() == KeyCode.ENTER) {
                String msg = userMessageTextField.getText();
                // Create Message Object 
                Message m = new Message(user1,msg);
                // Add Message Object to the Observable List
                listOfMessages.add(m);
                System.out.println("New Message: "+ msg);
                userMessageTextField.clear();
            }
        });

        messagesArea.setCellFactory(new Callback<ListView<Message>,ListCell<Message>>() {

            @Override
            public ListCell<Message> call(ListView<Message> param) {
                            return new ListCell<>(){
                                private FXMLLoader loader;
                                private MessageBubbleController controller;

                                @Override
                                protected void updateItem(Message msg, boolean empty) {
                                    super.updateItem(msg, empty);
                                    if(empty|| msg == null) {
                                        setText(null);
                                        setGraphic(null);
                                        setStyle("-fx-background-color: transparent;");
                                    }else{
                                        if(loader==null) {
                                            try{
                                                loader = new FXMLLoader(getClass().getResource("/com/suhba/MessageBubble.fxml"));
                                                Parent root = loader.load();
                                                controller = loader.getController();
                                                setGraphic(root);
                                            }catch(IOException e){
                                                e.printStackTrace();
                                            }
                                        }
                                        if (controller != null ) {
                                            if (msg.getSender().equals(user1)){
                                            controller.setMessage(msg,true);
                                            }
                                            else{
                                            controller.setMessage(msg,false);
                                        }
                                    }
                                    
                                    }
                                    
                                };
                            };
                
            }
            
        });

        chatsListView.setCellFactory(lv -> new ListCell<Chat>() {
            private ChatUserBoxController controller;
            private Node rootNode;
        
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/suhba/ChatUserBox.fxml"));
                    rootNode = loader.load();
                    controller = loader.getController();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        
            @Override
            protected void updateItem(Chat chat, boolean empty) {
                super.updateItem(chat, empty);
                if (empty || chat == null) {
                    setGraphic(null);
                    setText(null);
                    //setStyle("-fx-background-color:  #F9F7F7;");
                } else {
                    controller.setUserChat(chat);
                    setGraphic(rootNode);
                }
            }
        });

        
    }

    public void goToScene(String fxmlName) {
        // FXMLLoader loader = new  FXMLLoader(App.class.getResource( "/gov/iti/jets/ChatUI.fxml"));
            // Parent root = loader.load();
            //ClientController ch = loader.getController();
           // ch.setName(getName());
           // Stage stage = (Stage) nameTextField.getScene().getWindow();
            // stage.setScene(new Scene(root));
            // stage.show();
    }


}

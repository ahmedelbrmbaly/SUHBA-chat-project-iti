package com.suhba.controllers.components;

import java.net.URL;
import java.util.ResourceBundle;

import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.services.controllers.ChatScreenService;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class ChatListController implements Initializable{
    @FXML
    private ListView<User> chatsListView;

    @FXML
    private VBox chatsVBox;

    ObservableMap<User, Message> allChats;

    ChatScreenService chatScreenService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }



}

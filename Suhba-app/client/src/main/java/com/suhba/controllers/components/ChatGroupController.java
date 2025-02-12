package com.suhba.controllers.components;

import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class ChatGroupController {

    @FXML
    private Label groupLastMessage;

    @FXML
    private Label groupName;

    @FXML
    private ImageView groupPic;

    public void setUserGroup(Group group, Message lastMessage) {
        if(group.getGroupPhoto()==null){
            groupPic.setImage(new Image(getClass().getResourceAsStream("/images/defaultGroup.png")));
        }else{
            // groupPic.setImage((Image) group.getGroupPhoto());
        }
        groupName.setText(group.getGroupName());
        groupLastMessage.setText(lastMessage.getContent());
        Circle circle = new Circle(25,25,25);
        groupPic.setClip(circle);
    }

}

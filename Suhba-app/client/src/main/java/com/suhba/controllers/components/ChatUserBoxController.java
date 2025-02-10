
package com.suhba.controllers.components;


import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.models.Chat;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class ChatUserBoxController{

    @FXML
    private Circle statusCircle;

    @FXML
    private ImageView userChatImg;

    @FXML
    private Label userChatName;

    @FXML
    private Label userLastMessageLabel;

    
    // public ChatUserBoxController(){
    //     this.username = username;
    //     this.lastMessage = lastMessage;
    //     this.userImage = userImage;
    // }


    public void setUserChat(Chat chat){
        userChatName.setText(chat.getParticipant().getUsername());
        userChatImg.setImage(chat.getParticipant().getAvatar());
        userLastMessageLabel.setText(chat.getLastMessage().getContent());
        Circle circle = new Circle(25,25,25);
        userChatImg.setClip(circle);
    }


    public void setUserChat(User user, Message lastMessage) {
        if (user.getPicture() == null) {
            userChatImg.setImage(new Image(getClass().getResourceAsStream("/images/defaultUser.png")));
        }else{
            userChatImg.setImage((Image) user.getPicture());
        }
        userChatName.setText(user.getDisplayName());
        userLastMessageLabel.setText(lastMessage.getContent());
        Circle circle = new Circle(25,25,25);
        userChatImg.setClip(circle);
    }

    


}

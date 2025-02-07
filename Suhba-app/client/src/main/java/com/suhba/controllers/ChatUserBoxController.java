// package com.suhba.controllers;

// import com.suhba.models.Chat;
// import com.suhba.models.enums.OnlineStatus;
// import javafx.fxml.FXML;
// import javafx.scene.control.Label;
// import javafx.scene.image.ImageView;
// import javafx.scene.shape.Circle;
// import javafx.beans.binding.Bindings;

// public class ChatUserBoxController {
//     @FXML
//     private Circle statusCircle;

//     @FXML
//     private Circle unreadCountCircle;

//     @FXML
//     private Label unreadCountLabel;

//     @FXML
//     private ImageView userChatImg;

//     @FXML
//     private Label userChatName;

//     @FXML
//     private Label userLastMessageLabel;


//     public void setChat(Chat chat) {
//         // Avatar image setup
//         userChatImg.imageProperty().bind(chat.participantProperty().get().avatarProperty());
//         setupAvatarClip();
        
//         // Bind username
//         userChatName.textProperty().bind(chat.participantProperty().get().usernameProperty());
        
//         // Online status binding
//         chat.participantProperty().get().onlineStatusProperty().addListener(
//             (obs, oldStatus, newStatus) -> updateStatusCircle(newStatus)
//         );
        
//         updateStatusCircle(chat.getParticipant().getOnlineStatus());
        
//         // Last message binding with null safety
//         userLastMessageLabel.textProperty().bind(
//             Bindings.createStringBinding(() -> 
//                 chat.getLastMessage() != null ? 
//                 chat.getLastMessage().getContent() : "No messages",
//                 chat.lastMessageProperty()
//             )
//         );
        
//         // Unread count binding
//         unreadCountLabel.textProperty().bind(chat.unreadCountProperty().asString());
//         unreadCountCircle.visibleProperty().bind(chat.unreadCountProperty().greaterThan(0));
//     }

//     private void setupAvatarClip() {
//         Circle clip = new Circle();
//         clip.radiusProperty().bind(userChatImg.fitWidthProperty().divide(2));
//         clip.centerXProperty().bind(userChatImg.fitWidthProperty().divide(2));
//         clip.centerYProperty().bind(userChatImg.fitHeightProperty().divide(2));
//         userChatImg.setClip(clip);
//     }

//     private void updateStatusCircle(OnlineStatus status) {
//         statusCircle.setFill(status.getColor());
//     }
// }

package com.suhba.controllers;


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
        }
        userChatName.setText(user.getDisplayName());
        userLastMessageLabel.setText(lastMessage.getContent());
        Circle circle = new Circle(25,25,25);
        userChatImg.setClip(circle);
    }

    


}

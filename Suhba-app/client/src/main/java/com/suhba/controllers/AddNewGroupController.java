package com.suhba.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.database.enums.MessageStatus;
import com.suhba.services.MessagingService;
import com.suhba.services.UserService;
import com.suhba.services.controllers.GroupScreenService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AddNewGroupController implements Initializable {

    @FXML
    private BorderPane addNewFriendScreen;

    @FXML
    private Button chooseImageGroup;

    @FXML
    private Button createBtn;

    @FXML
    private TextField groupNameField;

    @FXML
    private ImageView imageGroup;

    @FXML
    private TextField searchField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ListView<User> groupListView;

    ObservableList<User> users;
    ObservableList<User> selectedUsers = FXCollections.observableArrayList();

    UserService userService;
    GroupScreenService groupService;
    MessagingService msgService;

    long currentUserId;

    public void setUserId(long userId) {
        currentUserId = userId;
        System.out.println("Received userId: " + userId);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Hi " + currentUserId);

        // load all friends in list
        // try {
            groupService = new GroupScreenService();
            userService = new UserService();
            msgService = new MessagingService();
            List<User> myFriends = groupService.getMyFriends(currentUserId);
            users = FXCollections.observableArrayList(myFriends);

            groupListView.setItems(users);
            groupListView.setSelectionModel(null);
            groupListView.setStyle("-fx-background-color: transparent;");

            setupListView();
        // } 

    }

    private void setupListView() {
        groupListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> listView) {
                return new ListCell<User>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        setStyle("-fx-background-color: transparent;");
                        if (empty || user == null) {
                            setGraphic(null);

                        } else {
                            try {
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource("/com/suhba/AddFriendGroupCell.fxml"));
                                HBox item = loader.load();

                                AddFriendGroupBoxController controller = loader.getController();
                                controller.setUser(user);

                                controller.addCheckBox.setOnAction(event -> {
                                    if (controller.isSelected()) {
                                        selectedUsers.add(user);
                                    } else {
                                        selectedUsers.remove(user);
                                    }
                                });

                                setGraphic(item);
                                setStyle("-fx-background-color: transparent;");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
            }
        });
    }

    @FXML
    void createNewGroup(ActionEvent event) {
        List<Long> groupMembersIds= new ArrayList<>();
        String groupName= groupNameField.getText();
        String groupDesc = descriptionArea.getText();
        String groupPic ;
        
        if(groupName==null){
            System.out.println("Must have group name!");
        }else{
            // Handle Creation
        if (selectedUsers.isEmpty()) {
            System.out.println("No selected user selected");
        } else {
            if(selectedUsers.size()<2){
                System.out.println("Cann't Create Group less than 3 members");
            }else{
                groupMembersIds.add(currentUserId);
                for (User user : selectedUsers) {
                    System.out.println(user);
                    groupMembersIds.add(user.getUserId());
                }
                for(Long ids: groupMembersIds){
                    System.out.println("Mem= " +ids);
                }
                Group newGroup = new Group(groupName,null,groupDesc,null);
                Group after = groupService.createNewGroup(newGroup, groupMembersIds);
                if(after.getGroupId()<0){
                    System.out.println("No group created!");
                }else{
                    System.out.println("Group created succesfully");
                    System.out.println("Group id= "+after.getGroupId()+" chatId= "+ after.getChatId());
                    try {
                        msgService.sendMessageToUser(new Message(currentUserId,after.getChatId(),"Hello new Group",MessageStatus.Sent,null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        
            
        }
        Node currentNode = (Node) event.getSource();
        Stage owner = (Stage) currentNode.getScene().getWindow();
        owner.getOwner().getScene().getRoot().setEffect(null);
        owner.close();
        Stage mainStage = (Stage) owner.getOwner();
        if (mainStage != null) {
            mainStage.setIconified(false);
            mainStage.requestFocus();
        }
    }

    @FXML
    void handleChooseImageGroup(ActionEvent event) {

    }

    @FXML
    void handleGroupName(ActionEvent event) {

    }

    @FXML
    void handleImageGroup(MouseEvent event) {

    }

    @FXML
    void handleSearch(ActionEvent event) {

    }

}

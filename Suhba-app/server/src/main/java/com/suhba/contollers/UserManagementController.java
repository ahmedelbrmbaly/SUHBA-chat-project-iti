package com.suhba.contollers;

import com.suhba.daos.interfaces.UserDAO;
import com.suhba.database.entities.User;
import com.suhba.utils.ScreenNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class UserManagementController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Long> userId;

    @FXML
    private TableColumn<User, String> userName;

    @FXML
    private TableColumn<User, String> phoneNumber;

    @FXML
    private TableColumn<User, String> userStatus;

    @FXML
    private Button addUserBtn;

    @FXML
    private TextField allUsersField;

    @FXML
    private Button filterBtn;

    @FXML
    private Pagination numberPagination;

    @FXML
    private TextField searchByPhone;

    @FXML
    private Label showingUsersLabel;

    private UserDAO userDAO; // Assume it's injected or initialized elsewhere

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        userId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userName.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load data into TableView
        loadUserData();
    }

    private void loadUserData() {
        List<User> users = userDAO.getAllUsers();
        if (users != null) {
            userList.setAll(users);
            userTable.setItems(userList);
        }
    }

    @FXML
    void handleAddUserBtn(ActionEvent event) {
        // Logic to add a user
    }

    @FXML
    void handleAllUsersField(ActionEvent event) {
        // Logic for all users field
    }

    @FXML
    void handleFilterBtn(ActionEvent event) {
        // Logic for filtering users
    }

    @FXML
    void goToBrodcastingScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "Broadcast.fxml");
    }

    @FXML
    void goToServerManagmentScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "serverManagement.fxml");
    }

    @FXML
    void goToSettingsScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "settings.fxml");
    }

    @FXML
    void goToStatisticsScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "Statics.fxml");
    }

    @FXML
    void goToUserManagmentScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "User Management.fxml");
    }

    @FXML
    void handleLogOut(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "login.fxml");
    }
}

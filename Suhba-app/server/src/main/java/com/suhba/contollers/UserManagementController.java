package com.suhba.contollers;

import com.suhba.database.entities.User;
import com.suhba.database.enums.UserStatus;
import com.suhba.daos.interfaces.UserDAO;
import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.utils.ScreenNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class UserManagementController {

    @FXML
    private Button addUserBtn;

    @FXML
    private Label allUsersField;

    @FXML
    private Button filterBtn;

    @FXML
    private Pagination numberPagination;

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
    private TextField searchByPhone;

    @FXML
    private Label showingUsersLabel;

    private UserDAO userDAO = new UserDAOImpl(); // Initialize UserDAO directly

    @FXML
    public void initialize() {
        setupTableColumns();
        loadUsers();
        userTable.setEditable(true);
    }

    private void setupTableColumns() {
        userId.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getUserId()).asObject());
        userName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplayName()));
        phoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        userStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getUserStatus() != null ? cellData.getValue().getUserStatus().toString() : "N/A"
                )
        );

        userName.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        userStatus.setCellFactory(TextFieldTableCell.forTableColumn());

        userName.setOnEditCommit(event -> updateUserField(event.getRowValue(), "name", event.getNewValue()));
        phoneNumber.setOnEditCommit(event -> updateUserField(event.getRowValue(), "phone", event.getNewValue()));
        userStatus.setOnEditCommit(event -> updateUserField(event.getRowValue(), "status", event.getNewValue()));
    }

    private void updateUserField(User user, String field, String newValue) {
        switch (field) {
            case "name":
                user.setDisplayName(newValue);
                break;
            case "phone":
                user.setPhone(newValue);
                break;
            case "status":
                try {
                    user.setUserStatus(UserStatus.valueOf(newValue.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid UserStatus value: " + newValue);
                    return;
                }
                break;
        }
        userDAO.updateUser(user);
        loadUsers();
    }

    private void loadUsers() {
        List<User> userList = userDAO.getAllUsers();
        ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
        userTable.setItems(observableUserList);
        showingUsersLabel.setText("Total Users: " + userList.size()); // Update label with user count
    }

    @FXML
    void handleAddUserBtn() {
        User newUser = new User();
        newUser.setDisplayName("New User");
        newUser.setPhone("000-000-0000");
        newUser.setUserStatus(UserStatus.Available);

        userDAO.addNewUser(newUser);
        loadUsers();
    }

    @FXML
    void handleFilterByPhoneBtn() {
        String phone = searchByPhone.getText().trim();

        if (phone.isEmpty()) {
            loadUsers(); // Reload all users if search field is empty
            return;
        }

        User user = userDAO.getUserByPhone(phone); // Fetch user from the database

        if (user != null) {
            ObservableList<User> filteredList = FXCollections.observableArrayList(user);
            userTable.setItems(filteredList); // Set filtered user in the table
        } else {
            System.out.println("No user found with phone: " + phone);
            userTable.setItems(FXCollections.observableArrayList()); // Clear table if no user found
        }
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

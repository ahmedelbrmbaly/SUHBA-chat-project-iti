package com.suhba.contollers;

import com.suhba.utils.ScreenNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class UserManagementController {

    @FXML
    private Button addUserBtn;

    @FXML
    private TextField allUsersField;

    @FXML
    private Button filterBtn;

    @FXML
    private Pagination numberPagination;

    @FXML
    private TableColumn<?, ?> phoneNumber;

    @FXML
    private TextField searchByPhone;

    @FXML
    private Label showingUsersLabel;

    @FXML
    private TableColumn<?, ?> userId;

    @FXML
    private TableColumn<?, ?> userName;

    @FXML
    private TableColumn<?, ?> userStatus;

    @FXML
    void handleAddUserBtn(ActionEvent event) {

    }

    @FXML
    void handleAllUsersField(ActionEvent event) {

    }

    @FXML
    void handleFilterBtn(ActionEvent event) {

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

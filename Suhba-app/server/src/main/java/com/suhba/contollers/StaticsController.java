package com.suhba.contollers;

import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.daos.interfaces.UserDAO;
import com.suhba.utils.ScreenNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.Map;

public class StaticsController {

    private UserDAO userDAO = new UserDAOImpl();

    @FXML
    private Button countryStatisticsBtn;

    @FXML
    private PieChart statisticsPieChart;

    @FXML
    private Button genderStatisticsBtn;

    @FXML
    private Button statusStatisticsBtn;

    @FXML
    private Button userEntriesBtn;

    @FXML
    public void initialize() {
        Map<com.suhba.database.enums.Country, Long> countryData = userDAO.getUsersCountries();
        updatePieChart("User Distribution by Country", countryData);
    }

    @FXML
    void handleCountryStatisticsBtn(ActionEvent event) {
        Map<com.suhba.database.enums.Country, Long> countryData = userDAO.getUsersCountries();
        updatePieChart("User Distribution by Country", countryData);
        countryStatisticsBtn.setStyle("-fx-background-color:  #3F72AF; -fx-text-fill: white; -fx-cursor:  hand;");
        genderStatisticsBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill:  #3F72AF; -fx-cursor:  hand;");
        statusStatisticsBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill:  #3F72AF; -fx-cursor:  hand;");

    }

    @FXML
    void handleGenderStatisticsBtn(ActionEvent event) {
        Map<com.suhba.database.enums.Gender, Long> genderData = userDAO.getUsersGenders();
        updatePieChart("User Distribution by Gender", genderData);
        genderStatisticsBtn.setStyle("-fx-background-color:  #3F72AF; -fx-text-fill: white; -fx-cursor:  hand;");
        countryStatisticsBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill:  #3F72AF; -fx-cursor:  hand;");
        statusStatisticsBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill:  #3F72AF; -fx-cursor:  hand;");
    }

    @FXML
    void handleStatusStatisticsBtn(ActionEvent event) {
        Map<com.suhba.database.enums.UserStatus, Long> statusData = userDAO.getUsersStatus();
        updatePieChart("User Distribution by Status", statusData);
        statusStatisticsBtn.setStyle("-fx-background-color:  #3F72AF; -fx-text-fill: white; -fx-cursor:  hand;");
        genderStatisticsBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill:  #3F72AF; -fx-cursor:  hand;");
        countryStatisticsBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill:  #3F72AF; -fx-cursor:  hand;");
    }

    private <T> void updatePieChart(String title, Map<T, Long> dataMap) {
        if (dataMap == null || dataMap.isEmpty()) {
            showAlert("No Data", "There is no data available for this category.");
            return;
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<T, Long> entry : dataMap.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey().toString(), entry.getValue()));
        }

        statisticsPieChart.setTitle(title);
        statisticsPieChart.setData(pieChartData);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

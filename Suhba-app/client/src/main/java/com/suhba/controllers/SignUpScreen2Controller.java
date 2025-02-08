package com.suhba.controllers;

import com.suhba.App;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.services.controllers.SignUp2Service;
import com.suhba.utils.ImageConvertion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SignUpScreen2Controller implements Initializable {

    @FXML
    private Button chooseImageBtn;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private Label signInLabel;

    @FXML
    private Button signUpBtn;

    @FXML
    private BorderPane signUpScreen2;

    @FXML
    private CheckBox termsCheckBox;

    @FXML
    private TextField usernameField;

    @FXML
    private RadioButton maleRadiobtn, femaleRadiobtn;

    @FXML
    private Circle pictureImageview;

    SignUp2Service myServices = new SignUp2Service();

    List<Country> countryList = new ArrayList<>(Arrays.asList(Country.values()));

    Image myImage;

    Blob imageBlob;

    ImageConvertion imageConvertion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(countryList);
        countryComboBox.getItems().addAll(countryList);

        pictureImageview.setStroke(Color.valueOf("#3f72af"));
        myImage = new Image(getClass().getResource("/images/profile.png").toExternalForm());
        pictureImageview.setFill(new ImagePattern(myImage));

        imageConvertion = new ImageConvertion();
    }

    @FXML
    void handleChooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");

        // Set extension filters to accept only JPG and PNG image files.
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files", "*.jpg", "*.JPG");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files", "*.png", "*.PNG");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);

        // Open the file chooser dialog.
        File selectedFile = fileChooser.showOpenDialog(App.myStage);
        if (selectedFile != null) {
            try {
                // Load the image from the selected file.
                myImage = new Image(selectedFile.toURI().toString());
                // Set the loaded image into the ImageView.
                pictureImageview.setFill(new ImagePattern(myImage));

                // Convert the image to Blob to store it into the database
                imageBlob = imageConvertion.convertImageToBlob(selectedFile);

            } catch (IOException | SQLException e) {
                System.out.println("Unable reach the image you have selected!");
            }
        }

    }

    @FXML
    void handleCountrySelection(ActionEvent event) {
        System.out.println(countryComboBox.getValue());
    }

    @FXML
    void handleDateOfBirth(ActionEvent event) {
        System.out.println(dateOfBirthPicker.getValue().toString());
    }

    @FXML
    void handleSignUp(ActionEvent event) {
        Gender gender = null;
        if (maleRadiobtn.isSelected())  gender = Gender.valueOf(maleRadiobtn.getText());
        else if (femaleRadiobtn.isSelected())  gender = Gender.valueOf(femaleRadiobtn.getText());
       /* if (myServices.checkInfo(usernameField.getText(), gender, LocalDate.parse(dateOfBirthPicker.getValue().toString()), countryComboBox.getValue(), imageBlob))
            myServices.moveToNextPage(event, "signInPage1.fxml");
*/
        if (myServices.checkInfo(usernameField.getText(), gender, LocalDate.parse(dateOfBirthPicker.getValue().toString()), countryComboBox.getValue()))
            myServices.moveToNextPage(event, "signInPage1.fxml");
    }

    @FXML
    void navigateToSignIn(MouseEvent event) {
        myServices.moveToNextPage(event, "signInPage1.fxml");
    }

}

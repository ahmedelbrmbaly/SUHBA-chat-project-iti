module com.suhba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;

    // Allow JavaFX to access controllers for FXML binding
    opens com.suhba.contollers to javafx.fxml;

    // Allow JavaFX to instantiate the App class
    opens com.suhba to javafx.graphics, javafx.fxml;

    // Export required packages
    exports com.suhba;
    exports com.suhba.services.client.interfaces;
    exports com.suhba.network to java.rmi;
    exports com.suhba.contollers;
}

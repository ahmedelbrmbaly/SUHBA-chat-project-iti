module com.suhba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;
    requires javafx.web;

    opens com.suhba to javafx.graphics, javafx.fxml;
    opens com.suhba.contollers to javafx.fxml; // Fix: Open controllers package for JavaFX

    exports com.suhba;
    exports com.suhba.services.client.interfaces;
    exports com.suhba.network to java.rmi;
    exports com.suhba.contollers;
}

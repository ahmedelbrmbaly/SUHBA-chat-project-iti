module com.suhba {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.rmi;

    opens com.suhba.controllers to javafx.fxml, javafx.base;
    opens com.suhba to javafx.fxml;
    exports com.suhba;
}

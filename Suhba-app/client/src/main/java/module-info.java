module com.suhba {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.rmi;
    requires java.desktop;
    requires java.sql.rowset;

    opens com.suhba.controllers to javafx.fxml, javafx.base;
    opens com.suhba to javafx.fxml;
    exports com.suhba;
}

module com.suhba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.suhba to javafx.fxml;
    exports com.suhba;
}

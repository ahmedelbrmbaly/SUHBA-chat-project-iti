module com.suhba {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.suhba to javafx.fxml;
    exports com.suhba;
}

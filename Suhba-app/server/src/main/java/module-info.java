module com.suhba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;

    opens com.suhba to javafx.fxml;
    opens com.suhba to javafx.graphics, javafx.fxml;

    exports com.suhba;
    exports com.suhba.services.client.interfaces;
    exports com.suhba.network to java.rmi;
    exports com.suhba.contollers;

}

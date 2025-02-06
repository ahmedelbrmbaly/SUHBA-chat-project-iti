////module com.suhba {
////    requires javafx.controls;
////    requires javafx.fxml;
////    requires java.sql;
////
////    exports com.suhba.contollers to javafx.fxml;
////    opens com.suhba to javafx.graphics;
////
//////    opens com.suhba to javafx.fxml;
////    exports com.suhba;
////}
//
//module com.suhba {
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires java.sql;
//    // Use "opens" for reflective access to private fields
//    opens com.suhba.contollers to javafx.fxml;
//
//    // Export any other necessary packages (if needed by other modules explicitly)
//    exports com.suhba.contollers;
//}


module com.suhba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Allow JavaFX to access controllers for FXML binding
    opens com.suhba.contollers to javafx.fxml;

    // Allow JavaFX to instantiate the App class
    opens com.suhba to javafx.graphics, javafx.fxml;

    // Export required packages
    exports com.suhba;
    exports com.suhba.contollers;
}

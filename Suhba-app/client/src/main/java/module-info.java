module com.suhba {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.rmi;

    opens com.suhba.controllers to javafx.fxml, javafx.base;
    opens com.suhba to javafx.fxml;

    // ✅ Allow RMI to access `com.suhba.network`
    exports com.suhba.network to java.rmi;

    exports com.suhba;
}


//module com.suhba {
//    requires javafx.controls;
//    requires java.sql;
//    requires javafx.fxml;
//    requires javafx.graphics;
//    requires javafx.base;
//    requires java.rmi;
//
//    opens com.suhba.controllers to javafx.fxml, javafx.base;
//    opens com.suhba to javafx.fxml;
//
//    // 🔹 Export com.suhba.network so RMI can access it
//    exports com.suhba.network;
//
//    // 🔹 Allow reflection for RMI
//    opens com.suhba.network to java.rmi;
//}

//module com.suhba {
//        requires javafx.controls;
//        requires java.sql;
//        requires javafx.fxml;
//        requires javafx.graphics;
//        requires javafx.base;
//        requires java.rmi;
//
//        opens com.suhba.controllers to javafx.fxml, javafx.base;
//        opens com.suhba to javafx.fxml;
//        exports com.suhba;
//        }

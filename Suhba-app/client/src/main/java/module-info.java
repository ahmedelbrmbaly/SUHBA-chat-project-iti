module com.suhba {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.rmi;
    requires java.desktop;
    requires java.sql.rowset;
    requires java.base;
    requires okhttp3;
    requires org.json;

    opens com.suhba.controllers to javafx.fxml, javafx.base;
    opens com.suhba to javafx.fxml;

    // ✅ Allow RMI to access `com.suhba.network`
    exports com.suhba.network to java.rmi;

    exports com.suhba;
    opens com.suhba.controllers.components to javafx.fxml;
    exports com.suhba.database.entities to java.rmi;
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

package com.suhba.utils;

import com.suhba.App;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class FXMLLoader {
    private Pane view;
    public static int pageIndx = 0;

//    public FXMLLoader () {
//        pageIndx++;
//    }

    public Pane getPane (String pageName) throws IOException {
        URL fileURL = App.class.getResource("/com/suhba/" +  pageName + ".fxml");
        view = javafx.fxml.FXMLLoader.load(fileURL);
        view.setUserData(pageIndx);
        pageIndx++;
        return view;
    }
}

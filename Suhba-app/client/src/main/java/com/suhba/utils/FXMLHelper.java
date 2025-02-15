package com.suhba.utils;

import com.suhba.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class FXMLHelper {
    private FXMLLoader loader;

    public Pane getPane(String pageName) throws IOException {
        URL fileURL = App.class.getResource("/com/suhba/" + pageName + ".fxml");
        if (fileURL == null) {
            throw new IOException("FXML file not found: " + pageName);
        }

        loader = new FXMLLoader(fileURL);
        Pane view = loader.load();
        return view;
    }

    public Object getController() {
        return loader.getController();
    }
}

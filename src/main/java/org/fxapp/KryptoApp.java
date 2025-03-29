package org.fxapp;

import javafx.application.Application;
import javafx.stage.Stage;

public class KryptoApp extends Application {
    private static final String TITLE = "Krypto";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);
        Navigator.switchPage(DESController.class, stage);
    }
}



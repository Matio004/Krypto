package org.fxapp;import javafx.application.Application;
import javafx.stage.Stage;

public class KryptoApp extends Application {
    private static final String TITLE = "Krypto";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);
        Navigator.switchPage(SchnorrController.class, stage); //change controller for the use of DES if intended
    }
}



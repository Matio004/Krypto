package org.fxapp;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class AbstractController implements Initializable {
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

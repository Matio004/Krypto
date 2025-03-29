package org.fxapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class Navigator {
    private static final String CONTROLLER = "Controller";
    private static Locale locale = Locale.ENGLISH;
    private static String lang = "English";


    public static void switchPage(Class<? extends AbstractController> controller, Stage stage) {
        try {
            URL fxml = getFxml(controller);
            //ResourceBundle resourceBundle = getResourceBundle(controller);
            FXMLLoader loader = new FXMLLoader(fxml);
            Parent parent = loader.load();
            AbstractController abstractController = loader.getController();

            abstractController.setStage(stage);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static URL getFxml(Class<? extends AbstractController> controller) {
        String name = controller.getSimpleName();

        if (name.endsWith(CONTROLLER) && name.length() > CONTROLLER.length()) {
            return controller.getResource(name.substring(0, name.length() - CONTROLLER.length()) + "Form.fxml");
        }
        throw new RuntimeException("Wrong controller name!");
    }

    private static ResourceBundle getResourceBundle(Class<? extends AbstractController> controller) {
        String name = controller.getSimpleName();

        if (name.endsWith(CONTROLLER) && name.length() > CONTROLLER.length()) {
            return ResourceBundle.getBundle(
                    name.substring(0, name.length() - CONTROLLER.length()) + "Form",
                    locale
            );
        }
        throw new RuntimeException("Wrong resource name!");
    }

    public static Locale getLocale() {
        return locale;
    }

    public static String getLang() {
        return lang;
    }

    public static void setLang(String lang) {
        Navigator.lang = lang;
        Navigator.locale = switch (lang) {
            case "English" -> Locale.ENGLISH;
            case "Polski" -> Locale.forLanguageTag("pl");
            default -> Locale.ROOT;
        };
    }
}

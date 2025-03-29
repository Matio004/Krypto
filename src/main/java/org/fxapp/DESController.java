package org.fxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.example.DES;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DESController extends AbstractController {
    @FXML
    public TextField loadFileText;
    @FXML
    public Button loadFileBttn;
    @FXML
    public TextArea textField;
    @FXML
    public TextArea cipherTextFiled;
    @FXML
    public Button saveFileBttn1;
    @FXML
    public TextField saveFileText1;
    @FXML
    public TextField keyInput;
    @FXML
    public Button saveFileBttn;
    @FXML
    public TextField saveFileText;
    @FXML
    public TextField loadFileText1;
    @FXML
    public Button loadFileBttn1;

    private boolean modeFile = false;

    private byte[] bytes;
    private byte[] encodedBytes;

    private DES des;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyInput.setTextFormatter(new TextFormatter<>(
                change -> {
                    if (!change.getText().matches("[0-9a-fA-F]*")) {
                        change.setText("");
                    }
                    if (keyInput.getLength() >= 16) {
                        change.setText("");
                    }
                    return change;
                }
        ));
    }

    @FXML
    public void onSchnorr(ActionEvent actionEvent) {
        //todo switch do schnorr
    }

    @FXML
    public void onLoadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj odszyfrowany plik");

        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            try (FileInputStream stream = new FileInputStream(file)) {
                bytes = stream.readAllBytes();
                textField.setText(new String(bytes));
            } catch (IOException _) {

            }
        } else {
            System.out.println("Load canceled");
        }
    }

    @FXML
    public void onLoadFile1(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj zaszyfrowany plik");

        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            try (FileInputStream stream = new FileInputStream(file)) {
                encodedBytes = stream.readAllBytes();
                cipherTextFiled.setText(new String(encodedBytes));
            } catch (IOException _) {

            }
        } else {
            System.out.println("Load canceled");
        }
    }

    @FXML
    public void decode(ActionEvent actionEvent) {
        /*if (!modeFile) {
            encodedBytes = cipherTextFiled.getText().getBytes();
        }*/
        bytes = des.decode(encodedBytes);
        textField.setText(new String(bytes));
    }

    @FXML
    public void encode(ActionEvent actionEvent) {
        if (!modeFile) {
            bytes = textField.getText().getBytes();
        }
        encodedBytes = des.encode(bytes);
        cipherTextFiled.setText(new String(encodedBytes));
    }

    @FXML
    public void onSaveFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz odszyfrowany plik");

        File file = fileChooser.showSaveDialog(getStage());
        if (file != null) {
            try (FileOutputStream stream = new FileOutputStream(file)) {
                stream.write(bytes);
            } catch (IOException _) {

            }
        } else {
            System.out.println("Save canceled");
        }
    }

    @FXML
    public void onSaveFile1(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz zaszyfrowany plik");

        File file = fileChooser.showSaveDialog(getStage());
        if (file != null) {
            try (FileOutputStream stream = new FileOutputStream(file)) {
                stream.write(encodedBytes);
            } catch (IOException _) {

            }
        } else {
            System.out.println("Save canceled");
        }
    }

    @FXML
    public void setKey(ActionEvent actionEvent) {
        des = new DES(keyInput.getText().getBytes());

    }

    @FXML
    public void onModeText(ActionEvent actionEvent) {
        modeFile = false;

        loadFileBttn.setDisable(true);
        loadFileText.setDisable(true);

        loadFileBttn1.setDisable(true);
        loadFileText1.setDisable(true);

        textField.setDisable(false);
        cipherTextFiled.setDisable(true);
    }

    @FXML
    public void onModeFile(ActionEvent actionEvent) {
        modeFile = true;

        loadFileBttn.setDisable(false);
        loadFileText.setDisable(false);

        loadFileBttn1.setDisable(false);
        loadFileText1.setDisable(false);

        textField.setDisable(true);
        cipherTextFiled.setDisable(true);
    }
}

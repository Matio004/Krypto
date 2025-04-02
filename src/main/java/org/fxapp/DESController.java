package org.fxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.scene.text.Text;
import org.example.DES;

import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class DESController extends AbstractController {
    @FXML
    public Text loadFileText;
    @FXML
    public Button loadFileBttn;
    @FXML
    public TextArea textField;
    @FXML
    public TextArea cipherTextFiled;
    @FXML
    public Button saveFileBttn1;
    @FXML
    public Text saveFileText1;
    @FXML
    public TextField keyInput;
    @FXML
    public Button saveFileBttn;
    @FXML
    public Text saveFileText;
    @FXML
    public Text loadFileText1;
    @FXML
    public Button loadFileBttn1;

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
    public void onLoadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj odszyfrowany plik");

        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            loadFileText.setText(file.toString());
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
            loadFileText1.setText(file.toString());
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
        if (des == null) {
            System.out.println("Nie ustawiono klucza");
            return;
        }
        try {
            bytes = des.decode(encodedBytes);
            textField.setText(new String(bytes));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void encode(ActionEvent actionEvent) {
        if (des == null) {
            System.out.println("Nie ustawiono klucza");
            return;
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
            saveFileText.setText(file.toString());
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
            saveFileText1.setText(file.toString());
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
        if (keyInput.getText().length() != 16) {
            System.out.println("Zły rozmiar klucza");
            return;
        }
        des = new DES(keyInput.getText());
    }

    @FXML
    public void onModeText(ActionEvent actionEvent) {
        loadFileBttn.setDisable(true);
        loadFileText.setDisable(true);

        loadFileBttn1.setDisable(true);
        loadFileText1.setDisable(true);

        textField.setDisable(false);
        cipherTextFiled.setDisable(true);
    }

    @FXML
    public void onModeFile(ActionEvent actionEvent) {

        loadFileBttn.setDisable(false);
        loadFileText.setDisable(false);

        loadFileBttn1.setDisable(false);
        loadFileText1.setDisable(false);

        textField.setDisable(true);
        cipherTextFiled.setDisable(true);
    }

    public void onText(KeyEvent keyEvent) {
        bytes = textField.getText().getBytes();
    }

    public void onCipheredText(KeyEvent keyEvent) {
        encodedBytes = textField.getText().getBytes();
    }

    @FXML
    public void generateKey(ActionEvent actionEvent) {
        Random random = new Random();

        char[] temp = "0123456789abcdef".toCharArray();
        keyInput.setText("");

        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            key.append(temp[random.nextInt(16)]);
        }

        keyInput.setText(key.toString());
    }
}

package org.fxapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Schnorr;
import org.example.Utils;

import java.awt.event.ActionEvent;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class SchnorrController extends AbstractController{
    private Stage stage;
    @FXML
    public TextArea messageTextArea;
    @FXML
    public TextArea signatureOutput;
    @FXML
    public Text verificationResult;
    @FXML
    public Button loadMessageBtn;
    @FXML
    public Text loadedFileLabel;
    @FXML
    public Button loadSignBtn;

    //TODO pokazać wygenerowany klucz w GUI

    private Schnorr schnorr;
    private byte[] messageBytes;
    private byte[] signatureBytes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schnorr = new Schnorr();
        loadedFileLabel.setText("Plik nie został załadowany");
        verificationResult.setText("Oczekiwanie na działanie...");
        messageTextArea.setText("");
        signatureOutput.setText("");
    }
    @FXML
    public void onLoadMessage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Załaduj plik");
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            loadedFileLabel.setText(file.getName());
            try (FileInputStream fis = new FileInputStream(file)) {
                signatureBytes = fis.readAllBytes();
                messageTextArea.setText(new String(messageBytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void onLoadSign() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Załaduj plik");
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            loadedFileLabel.setText(file.getName());
            try (FileInputStream fis = new FileInputStream(file)) {
                signatureBytes = fis.readAllBytes();
                signatureOutput.setText(new String(signatureBytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onGenerateKeys() {
        schnorr.KeyGenerator();
        verificationResult.setText("Klucze wygenerowane.");
    }

    @FXML
    public void onSignMessage() {
        String msg = messageTextArea.getText();
        if (msg.isEmpty()) {
            verificationResult.setText("Wpisz wiadomość.");
            return;
        }
        BigInteger[] sig = schnorr.signGenerator(msg);
        String output = Utils.bytesToHex(sig[0].toByteArray()) + "\n" +
                Utils.bytesToHex(sig[1].toByteArray());
        signatureOutput.setText(output);
        verificationResult.setText("Wiadomość podpisana.");
    }

    @FXML
    public void onVerifySignature() {
        String msg = messageTextArea.getText();
        String sig = signatureOutput.getText();
        if (msg.isEmpty() || sig.isEmpty()) {
            verificationResult.setText("Wiadomość lub podpis są puste.");
            return;
        }
        boolean valid = schnorr.stringSignVerificator(msg, sig);
        verificationResult.setText(valid ? "Podpis jest prawidłowy." : "Podpis jest nieprawidłowy.");
    }
    @FXML
    public void saveToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz do pliku");

        File file = fileChooser.showSaveDialog(getStage());
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(signatureOutput.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }


}

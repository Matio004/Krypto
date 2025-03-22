import org.example.DES;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class DESFrame extends JFrame {
    private DES des;
    private JTextField inputField;
    private JTextArea outputArea;
    private JTextArea keyArea;
    private JPanel buttonPanel = new JPanel();
    private byte[] cipher;
    private byte[] decipher;

    public DESFrame(String title) {
        super(title);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel inputLabel = new JLabel("Wprowadź bajty (rozdzielone przecinkami):");
        inputField = new JTextField(30);

        JLabel outputLabel = new JLabel("Wynik:");
        outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);


        JLabel keyLabel = new JLabel("Klucz (rozdzielony przecinkami, 8 bajtów):");
        keyArea = new JTextArea(1, 30);
        keyArea.setEditable(true);


        JButton generateKeyButton = new JButton("Generuj klucz");
        JButton encryptButton = new JButton("Szyfruj");
        JButton decryptButton = new JButton("Deszyfruj");

        generateKeyButton.addActionListener(this::generateKey);
        encryptButton.addActionListener(this::encryptText);
        decryptButton.addActionListener(this::decryptText);

        panel.add(inputLabel);
        panel.add(inputField);
        panel.add(keyLabel);
        panel.add(keyArea);
        buttonPanel.add(generateKeyButton);
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        add(buttonPanel, BorderLayout.SOUTH);
        panel.add(outputLabel);
        panel.add(new JScrollPane(outputArea));

        this.add(panel);
    }

    private void generateKey(ActionEvent e) {
        try {
            byte[] key = new byte[8];
            for (int i = 0; i < 8; i++) {
                key[i] = (byte) (Math.random() * 256);
            }
            keyArea.setText(Arrays.toString(key));
            des = new DES(key);
            JOptionPane.showMessageDialog(this, "Klucz został wygenerowany!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Błąd przy generowaniu klucza: " + ex.getMessage());
        }
    }

    private void encryptText(ActionEvent e) {
        try {
            byte[] inputText = parseInputBytes(inputField.getText());
            if (des == null) {
                JOptionPane.showMessageDialog(this, "Proszę najpierw wygenerować klucz.");
                return;
            }
            cipher = des.encode(inputText);
            outputArea.setText("Zaszyfrowane: " + Arrays.toString(cipher));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Błąd przy przetwarzaniu danych: " + ex.getMessage());
        }
    }

    private void decryptText(ActionEvent e) {
        try {
            byte[] inputCipher = parseInputBytes(inputField.getText());
            if (des == null) {
                JOptionPane.showMessageDialog(this, "Proszę najpierw wygenerować klucz.");
                return;
            }
            decipher = des.decode(inputCipher);
            outputArea.setText("Odszyfrowane: " + Arrays.toString(decipher));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Błąd przy przetwarzaniu danych: " + ex.getMessage());
        }
    }

    private byte[] parseInputBytes(String input) {
        String[] parts = input.split(",");
        byte[] bytes = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            bytes[i] = Byte.parseByte(parts[i].trim());
        }
        return bytes;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DESFrame desFrame = new DESFrame("DES");
            desFrame.setVisible(true);
        });
    }
}

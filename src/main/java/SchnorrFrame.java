import org.example.Schnorr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class SchnorrFrame extends JFrame {

    private Schnorr schnorr;
    private JTextArea messageTextArea;
    private JTextArea signatureTextArea;
    private JTextArea verificationResultTextArea;
    private JButton generateButton;
    private JButton signButton;
    private JButton verifyButton;

    public SchnorrFrame(String title) {
        super(title);

        schnorr = new Schnorr();

        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel messageLabel = new JLabel("Wiadomość:");
        messageTextArea = new JTextArea(5, 30);
        JScrollPane messageScroll = new JScrollPane(messageTextArea);
        panel.add(messageLabel);
        panel.add(messageScroll);

        JLabel signatureLabel = new JLabel("Podpis:");
        signatureTextArea = new JTextArea(5, 30);
        JScrollPane signatureScroll = new JScrollPane(signatureTextArea);
        signatureTextArea.setEditable(false);
        panel.add(signatureLabel);
        panel.add(signatureScroll);

        JLabel verificationLabel = new JLabel("Wynik weryfikacji:");
        verificationResultTextArea = new JTextArea(1, 30);
        verificationResultTextArea.setEditable(false);
        JScrollPane verificationScroll = new JScrollPane(verificationResultTextArea);
        panel.add(verificationLabel);
        panel.add(verificationScroll);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        generateButton = new JButton("Generuj klucze");
        signButton = new JButton("Podpisz");
        verifyButton = new JButton("Zweryfikuj podpis");

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                schnorr.KeyGenerator();
                JOptionPane.showMessageDialog(SchnorrFrame.this, "Klucze zostały wygenerowane!");
            }
        });

        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageTextArea.getText();
                if (message.isEmpty()) {
                    JOptionPane.showMessageDialog(SchnorrFrame.this, "Proszę wprowadzić wiadomość do podpisania.");
                    return;
                }

                byte[] messageBytes = message.getBytes();

                BigInteger[] signature = schnorr.signGenerator(messageBytes);
                signatureTextArea.setText("s1: " + signature[0].toString() + "\ns2: " + signature[1].toString());
            }
        });

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageTextArea.getText();
                if (message.isEmpty()) {
                    JOptionPane.showMessageDialog(SchnorrFrame.this, "Proszę wprowadzić wiadomość.");
                    return;
                }

                String[] signatureParts = signatureTextArea.getText().split("\n");
                if (signatureParts.length != 2) {
                    JOptionPane.showMessageDialog(SchnorrFrame.this, "Proszę wprowadzić poprawny podpis.");
                    return;
                }

                try {
                    BigInteger s1 = new BigInteger(signatureParts[0].split(": ")[1]);
                    BigInteger s2 = new BigInteger(signatureParts[1].split(": ")[1]);

                    byte[] messageBytes = message.getBytes();

                    boolean isVerified = schnorr.signVerificator(messageBytes, new BigInteger[]{s1, s2});
                    verificationResultTextArea.setText(isVerified ? "Podpis jest prawidłowy" : "Podpis jest nieprawidłowy");

                    System.out.println("Weryfikacja:");
                    System.out.println("s1: " + s1);
                    System.out.println("s2: " + s2);
                    System.out.println("Wiadomość: " + message);
                    System.out.println("Wynik weryfikacji: " + isVerified);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SchnorrFrame.this, "Proszę wprowadzić poprawny format podpisu.");
                }
            }
        });

        buttonPanel.add(generateButton);
        buttonPanel.add(signButton);
        buttonPanel.add(verifyButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SchnorrFrame("Schnorr - Algorytm podpisu").setVisible(true);
        });
    }
}

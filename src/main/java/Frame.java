import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    public Frame() {
        setTitle("Okno początkowe");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu algorithmMenu = new JMenu("Algorytm");
        JMenuItem DES = new JMenuItem("DES");
        JMenuItem Schnorr = new JMenuItem("Schnorr");
        algorithmMenu.add(DES);
        algorithmMenu.add(Schnorr);
        menuBar.add(algorithmMenu);

        setJMenuBar(menuBar);
        add(panel);

        DES.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DESFrame des = new DESFrame("DES");
                des.setVisible(true);
                des.toFront();
            }
        });
        Schnorr.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SchnorrFrame schnorr = new SchnorrFrame("Schnorr");
                schnorr.setVisible(true);
                schnorr.toFront();
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Frame().setVisible(true));
    }
}

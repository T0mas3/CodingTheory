package lt.tomasbareikis.GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Pradinis langas
 */
public class MainWindow {
    private JButton sendVectorButton;
    private JPanel rootJPanel;
    private JButton sendStringButton;

    public MainWindow() {
        sendVectorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new VectorWindow();
            }
        });

        sendStringButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new StringWindow();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sąsūkos kodas. Dekodavimas su grįžtamuoju ryšiu (A14). Tomas Bareikis. PS-2");
        frame.setContentPane(new MainWindow().rootJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

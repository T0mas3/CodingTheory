package lt.tomas.bareikis.GUI;


import javax.swing.*;
import java.awt.*;

public class ImageWindow extends JFrame {

    private JPanel rootJPanel;

    public ImageWindow() {
        super("Paveikslėlio siuntimas");

        rootJPanel.setPreferredSize(new Dimension(600, 600));

        this.setContentPane(rootJPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}

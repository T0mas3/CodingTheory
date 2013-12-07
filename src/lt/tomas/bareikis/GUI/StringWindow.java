package lt.tomas.bareikis.GUI;


import javax.swing.*;
import java.awt.*;

public class StringWindow extends JFrame {

    private JPanel rootJPanel;

    public StringWindow() {
        super("Teksto siuntimas");

        rootJPanel.setPreferredSize(new Dimension(600, 600));

        this.setContentPane(rootJPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

}

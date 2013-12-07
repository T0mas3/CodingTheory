package lt.tomas.bareikis.GUI;


import lt.tomas.bareikis.GUI.customComponents.ProbabilityJTextField;
import lt.tomas.bareikis.GUI.customComponents.StringInputJTextArea;
import lt.tomas.bareikis.GUI.customComponents.VectorInputJTextArea;

import javax.swing.*;
import java.awt.*;

public class ImageWindow extends JFrame {

    private JPanel rootJPanel;
    private JButton chooseImageButton;
    private JButton encodeButton;
    private VectorInputJTextArea encodedTextArea;
    private VectorInputJTextArea inputImageTextArea;
    private ProbabilityJTextField errorProbabilityTextField;
    private JButton sendChannelButton;
    private VectorInputJTextArea transferedVectorTextArea;
    private StringInputJTextArea transferedStringTextArea;
    private JButton decodeButton;

    public ImageWindow() {
        super("Paveikslėlio siuntimas");

        rootJPanel.setPreferredSize(new Dimension(700, 600));

        this.setContentPane(rootJPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}

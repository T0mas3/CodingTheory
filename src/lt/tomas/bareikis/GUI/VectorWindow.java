package lt.tomas.bareikis.GUI;

import lt.tomas.bareikis.GUI.customComponents.ProbabilityJTextField;
import lt.tomas.bareikis.GUI.customComponents.VectorInputJTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class VectorWindow extends JFrame {
    private VectorInputJTextArea initialTextArea;
    private VectorInputJTextArea transferedtextArea;
    private VectorInputJTextArea textArea3;
    private VectorInputJTextArea encodedTextArea;
    private JPanel rootJPanel;
    private JButton encodeButton;
    private JButton decodeButton;
    private ProbabilityJTextField errorProbabilityTextField;
    private JButton sendChannelButton;
    private JLabel errorProbabilityLabel;

    public VectorWindow() {
        super("Vektoriaus siuntimas");

        rootJPanel.setPreferredSize(new Dimension(600, 600));

        this.setContentPane(rootJPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (VectorWindow.this.initialTextArea.isInputValid()) {
                    VectorWindow.this.encodedTextArea.setText(
                            Helper.encodeVectorString(VectorWindow.this.initialTextArea.getText())
                    );
                } else {
                    VectorWindow.this.initialTextArea.showValidationError();
                }
            }
        });

        sendChannelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (VectorWindow.this.encodedTextArea.isInputValid()) {

                    float errorProbability = VectorWindow.this.errorProbabilityTextField.getErrorProbability();

                    VectorWindow.this.transferedtextArea.setText(
                            Helper.transferVectorString(VectorWindow.this.encodedTextArea.getText(), errorProbability)
                    );
                } else {
                    VectorWindow.this.transferedtextArea.showValidationError();
                }
            }
        });
    }


}

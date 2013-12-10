package lt.tomasbareikis.GUI;

import lt.tomasbareikis.GUI.components.ProbabilityJTextField;
import lt.tomasbareikis.GUI.components.VectorInputJTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public class VectorWindow extends JFrame {
    private VectorInputJTextArea initialTextArea;
    private VectorInputJTextArea transferedTextArea;
    private VectorInputJTextArea decodedTextArea;
    private VectorInputJTextArea encodedTextArea;
    private JPanel rootJPanel;
    private JButton encodeButton;
    private JButton decodeButton;
    private ProbabilityJTextField errorProbabilityTextField;
    private JButton sendChannelButton;
    private JLabel errorProbabilityLabel;
    private JScrollPane scrollPane1;
    private JList sendingErrorsList;
    private JLabel sendingErrorsCountLabel;
    private JList decodingErrorsList;
    private JLabel decodingErrorsCountLabel;

    private LinkedList<Integer> mismatchPositions;

    public VectorWindow() {
        super("Vektoriaus siuntimas");

        rootJPanel.setPreferredSize(new Dimension(600, 600));

        this.setContentPane(rootJPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setVisible(true);

        // Užkodavimo mygtuko paspaudimas
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

        // Siuntimo kanalu mygtuko paspaudimas
        sendChannelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (VectorWindow.this.encodedTextArea.isInputValid()) {

                    float errorProbability = VectorWindow.this.errorProbabilityTextField.getErrorProbability();

                    VectorWindow.this.transferedTextArea.setText(
                            Helper.transferVectorString(VectorWindow.this.encodedTextArea.getText(), errorProbability)
                    );
                } else {
                    VectorWindow.this.transferedTextArea.showValidationError();
                }
            }
        });

        // Dekodavimo mygtuko paspaudimas
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (VectorWindow.this.transferedTextArea.isInputValid()) {

                    VectorWindow.this.decodedTextArea.setText(
                            Helper.decodeVectorString(VectorWindow.this.transferedTextArea.getText())
                    );
                } else {
                    VectorWindow.this.decodedTextArea.showValidationError();
                }
            }
        });

        // Lyginame persiųstą kanalu vektorių su užkoduotu vektoriumi
        new VectorTextAreaCompareListener(transferedTextArea, encodedTextArea, sendingErrorsList, sendingErrorsCountLabel);
        // Lyginame dekoduotą vektorių su originaliu įvestu vektoriumi
        new VectorTextAreaCompareListener(decodedTextArea, initialTextArea, decodingErrorsList, decodingErrorsCountLabel);
    }

}

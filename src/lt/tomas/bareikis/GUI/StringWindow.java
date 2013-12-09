package lt.tomas.bareikis.GUI;


import lt.tomas.bareikis.GUI.customComponents.ProbabilityJTextField;
import lt.tomas.bareikis.GUI.customComponents.StringInputJTextArea;
import lt.tomas.bareikis.GUI.customComponents.VectorInputJTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StringWindow extends JFrame {

    private JPanel rootJPanel;
    private JButton encodeButton;
    private VectorInputJTextArea encodedTextArea;
    private ProbabilityJTextField errorProbabilityTextField;
    private JButton sendChannelButton;
    private VectorInputJTextArea transferedVectorTextArea;
    private StringInputJTextArea transferedStringTextArea;
    private JButton decodeButton;
    private StringInputJTextArea decodedTextArea;
    private StringInputJTextArea initialTextArea;
    private JList sendingEncodedErrorsList;
    private JLabel sendingEncodedErrorsCountLabel;
    private JLabel sendingUncodedErrorsCountLabel;
    private JLabel decodedErrorsCountLabel;
    private JList sendingUncodedErrorsList;
    private JList decodedErrorsList;

    public StringWindow() {
        super("Teksto siuntimas");

        rootJPanel.setPreferredSize(new Dimension(600, 700));

        this.setContentPane(rootJPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setVisible(true);

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringWindow.this.encodedTextArea.setText(
                        Helper.encodeVectorString(StringWindow.this.initialTextArea.getValueAsBinaryString())
                );
            }
        });

        sendChannelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float errorProbability = StringWindow.this.errorProbabilityTextField.getErrorProbability();

                StringWindow.this.transferedVectorTextArea.setText(
                        Helper.transferVectorString(StringWindow.this.encodedTextArea.getText(), errorProbability)
                );

                StringWindow.this.transferedStringTextArea.setText(
                        Helper.transformBitStringToCharactersString(
                                Helper.transferVectorString(StringWindow.this.initialTextArea.getValueAsBinaryString(), errorProbability)
                        )
                );
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringWindow.this.transferedVectorTextArea.isInputValid()) {

                    StringWindow.this.decodedTextArea.setText(
                            Helper.transformBitStringToCharactersString(
                                    Helper.decodeVectorString(StringWindow.this.transferedVectorTextArea.getText())
                            )
                    );
                } else {
                    StringWindow.this.transferedVectorTextArea.showValidationError();
                }
            }
        });

        new VectorTextAreaCompareListener(transferedVectorTextArea, encodedTextArea, sendingEncodedErrorsList, sendingEncodedErrorsCountLabel);
        new VectorTextAreaCompareListener(transferedStringTextArea, initialTextArea, sendingUncodedErrorsList, sendingUncodedErrorsCountLabel);
        new VectorTextAreaCompareListener(decodedTextArea, initialTextArea, decodedErrorsList, decodedErrorsCountLabel);

    }

}

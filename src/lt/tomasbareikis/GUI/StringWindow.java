package lt.tomasbareikis.GUI;


import lt.tomasbareikis.GUI.customComponents.ProbabilityJTextField;
import lt.tomasbareikis.GUI.customComponents.StringInputJTextArea;
import lt.tomasbareikis.GUI.customComponents.VectorInputJTextArea;

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

        // Užkodavimo mygtuko paspaudimas
        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringWindow.this.encodedTextArea.setText(
                        Helper.encodeVectorString(StringWindow.this.initialTextArea.getValueAsBinaryString())
                );
            }
        });

        // Siuntimo kanalu mygtuko paspaudimas
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

        // Dekodavimo mygtuko paspaudimas
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

        // Lyginame persiųstą kanalu vektorių su užkoduoto teksto vektoriumi
        new VectorTextAreaCompareListener(transferedVectorTextArea, encodedTextArea, sendingEncodedErrorsList, sendingEncodedErrorsCountLabel);
        // Lyginame persiųstą nekoduotą tekstą su pradiniu tekstu
        new VectorTextAreaCompareListener(transferedStringTextArea, initialTextArea, sendingUncodedErrorsList, sendingUncodedErrorsCountLabel);
        // Lyginame dekoduotą tekstą su pradiniu tekstu
        new VectorTextAreaCompareListener(decodedTextArea, initialTextArea, decodedErrorsList, decodedErrorsCountLabel);

    }

}

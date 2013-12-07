package lt.tomas.bareikis.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;


public class VectorWindow extends JFrame {
    private JTextArea initialTextArea;
    private JTextArea transferedtextArea;
    private JTextArea textArea3;
    private JTextArea encodedTextArea;
    private JPanel rootJPanel;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTextField errorProbabilityTextField;
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
                if (Helper.isVectorInputValid(VectorWindow.this.initialTextArea.getText())) {
                    VectorWindow.this.encodedTextArea.setText(
                            Helper.encodeVectorString(VectorWindow.this.initialTextArea.getText())
                    );
                } else {
                    JOptionPane.showMessageDialog(null, "Vektorius gali būti sudarytias tik iš 0 ir 1");
                }
            }
        });
        new TextAreaUpdateListener(initialTextArea);
        sendChannelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isVectorInputValid(VectorWindow.this.encodedTextArea.getText())) {

                    float errorProbability = 0;
                    try {
                        errorProbability = Helper.readInputAsFloat(VectorWindow.this.errorProbabilityTextField.getText());
                        System.out.println(errorProbability);
                    } catch (ParseException e1) {
                        JOptionPane.showMessageDialog(null, "Blogas tikimybės formatas. Tikimybė turi būti intervale [0, 1]");
                    } catch (IllegalArgumentException e2) {
                        JOptionPane.showMessageDialog(null, "Blogas tikimybės formatas. Tikimybė turi būti intervale [0, 1]");
                    }

                    VectorWindow.this.transferedtextArea.setText(
                            Helper.transferVectorString(VectorWindow.this.encodedTextArea.getText(), errorProbability)
                    );
                } else {
                    JOptionPane.showMessageDialog(null, "Vektorius gali būti sudarytias tik iš 0 ir 1");
                }
            }
        });
        new TextAreaUpdateListener(encodedTextArea);
    }


}

package lt.tomas.bareikis.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class VectorWindow extends JFrame {
    private JTextArea initialTextArea;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea encodedTextArea;
    private JPanel rootJPanel;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTextField textField1;
    private JButton sendChannelButton;
    private JLabel errorProbabilityTextField;

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
                if (Helper.isInputValid(VectorWindow.this.initialTextArea.getText())) {
                    VectorWindow.this.encodedTextArea.setText(
                            Helper.encodeVectorString(VectorWindow.this.initialTextArea.getText())
                    );
                } else {
                    JOptionPane.showMessageDialog(null, "Vektorius gali būti sudarytias tik iš 0 ir 1");
                }
            }
        });
        new TextAreaUpdateListener(initialTextArea);
    }


}

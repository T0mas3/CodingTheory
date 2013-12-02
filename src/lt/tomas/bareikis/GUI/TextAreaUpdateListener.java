package lt.tomas.bareikis.GUI;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextAreaUpdateListener implements DocumentListener {

    JTextArea jTextArea;

    public TextAreaUpdateListener(JTextArea jTextArea) {
        jTextArea.getDocument().addDocumentListener(this);
        this.jTextArea = jTextArea;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.validateInput();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.validateInput();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.validateInput();
    }

    private void validateInput() {
        if (Helper.isInputValid(jTextArea.getText())) {
            jTextArea.setForeground(Color.black);
        } else {
            jTextArea.setForeground(Color.magenta);
        }
    }
}

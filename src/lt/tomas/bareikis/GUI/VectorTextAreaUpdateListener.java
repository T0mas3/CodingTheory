package lt.tomas.bareikis.GUI;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Kas kartą pasikeitus tekstui vektoriaus lauke, jis yra validuojamas
 */
public class VectorTextAreaUpdateListener implements DocumentListener {

    JTextArea jTextArea;

    public VectorTextAreaUpdateListener(JTextArea jTextArea) {
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

    /**
     * Jei vektorius nėra validus (sudarytas ne tik iš 0 ir 1), tai tekstas nuspalvinamas kita spalva
     */
    private void validateInput() {
        if (this.isVectorInputValid(jTextArea.getText())) {
            jTextArea.setForeground(Color.black);
        } else {
            jTextArea.setForeground(Color.magenta);
        }
    }

    /**
     * Ar vektorius yra tinkamas (sudarytas tik iš 0 ir 1s)
     *
     * @param input tekstas
     * @return ar validus
     */
    private boolean isVectorInputValid(String input) {
        return input.matches("[0-1]*"); // Turi būti nulis ar daugiau 1 arba 0
    }
}

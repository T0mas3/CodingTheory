package lt.tomas.bareikis.GUI.customComponents;


import lt.tomas.bareikis.GUI.VectorTextAreaUpdateListener;

import javax.swing.*;
import javax.swing.text.Document;

/**
 * Laukas vektoriaus įvedimui. Validuoja įvestus duomenis.
 */
public class VectorInputJTextArea extends JTextArea {

    public VectorInputJTextArea() {
        super();
        init();
    }

    public VectorInputJTextArea(String text) {
        super(text);
        init();
    }

    public VectorInputJTextArea(int rows, int columns) {
        super(rows, columns);
        init();
    }

    public VectorInputJTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
        init();
    }

    public VectorInputJTextArea(Document doc) {
        super(doc);
        init();
    }

    public VectorInputJTextArea(Document doc, String text, int rows, int columns) {
        super(doc, text, rows, columns);
        init();
    }

    public boolean isInputValid() {
        return this.isVectorInputValid(this.getText());
    }

    public void showValidationError() {
        JOptionPane.showMessageDialog(null, "Vektorius gali būti sudarytias tik iš 0 ir 1");
    }

    private void init() {
        // Keliame tekstą į naują eilutę, jei netelpa
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        // Kas kartą pasikeitus teikstui, validuojame jį
        new VectorTextAreaUpdateListener(this);
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

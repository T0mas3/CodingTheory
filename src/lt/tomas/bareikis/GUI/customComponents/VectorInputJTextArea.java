package lt.tomas.bareikis.GUI.customComponents;


import lt.tomas.bareikis.GUI.Helper;
import lt.tomas.bareikis.GUI.VectorTextAreaUpdateListener;

import javax.swing.*;
import javax.swing.text.Document;

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
        return Helper.isVectorInputValid(this.getText());
    }

    public void showValidationError() {
        JOptionPane.showMessageDialog(null, "Vektorius gali būti sudarytias tik iš 0 ir 1");
    }

    private void init() {
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        new VectorTextAreaUpdateListener(this);
    }

}

package lt.tomas.bareikis.GUI.customComponents;


import lt.tomas.bareikis.GUI.Helper;

import javax.swing.*;
import javax.swing.text.Document;

public class StringInputJTextArea extends JTextArea {

    public StringInputJTextArea() {
        super();
        init();
    }

    public StringInputJTextArea(String text) {
        super(text);
        init();
    }

    public StringInputJTextArea(int rows, int columns) {
        super(rows, columns);
        init();
    }

    public StringInputJTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
        init();
    }

    public StringInputJTextArea(Document doc) {
        super(doc);
        init();
    }

    public StringInputJTextArea(Document doc, String text, int rows, int columns) {
        super(doc, text, rows, columns);
        init();
    }

    private void init() {
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
    }

    public String getValueAsBinaryString() {
        return Helper.transformStringToBitString(this.getText());
    }

}

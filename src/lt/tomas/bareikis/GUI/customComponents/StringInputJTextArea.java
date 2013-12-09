package lt.tomas.bareikis.GUI.customComponents;


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
        // Keliame tekstą į naują eilutę, jei netelpa
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
    }

    public String getValueAsBinaryString() {
        return this.transformStringToBitString(this.getText());
    }

    private String transformStringToBitString(String input) {

        String bitString = "";

        // 16 bitų, o ne 8 tam, kad galėtume perduoti ir Lietuviškos abėcėlės simbolius
        int requiredLength = 16;

        for (int i = 0; i < input.length(); i++) {
            // Gauname kievieno simbolio kodą ir jį paverčiame dvejetainiu pavidalu (tekstinėje eilutėje)
            String singleCharBitString = Integer.toBinaryString((int)input.charAt(i));
            // Pridedame papildomus 0 priekyje, ilgis būtų 16 bitų
            if (singleCharBitString.length() < requiredLength) {
                int addZeroes = requiredLength - singleCharBitString.length();
                for (int n = 0; n < addZeroes; n++) {
                    singleCharBitString = "0" + singleCharBitString;
                }
            }

            bitString += singleCharBitString;
        }

        return bitString;
    }

}

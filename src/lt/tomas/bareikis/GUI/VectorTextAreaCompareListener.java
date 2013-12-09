package lt.tomas.bareikis.GUI;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.LinkedList;

/**
 * Lygina kelis teksto laukus, randa skirtumus, juos paryškina ir išveda į atskirą lauką.
 */
public class VectorTextAreaCompareListener implements DocumentListener, ListSelectionListener {

    private final JTextArea compareToTextArea;
    private final JList errorsList;
    private final JLabel errorsCountLabel;
    JTextArea jTextArea;
    LinkedList<Integer> mismatchesInStrings;

    /**
     *
     * @param jTextArea teksto laukas, kuris yra paryškinamas
     * @param compareToTextArea su kokiu lauku lyginama
     * @param errorsList sąrašas, kuriame bus rodomi klaidų indeksai
     * @param errorsCountLabel laukas, kuriame rodomas klaidų skaičius
     */
    public VectorTextAreaCompareListener(JTextArea jTextArea, JTextArea compareToTextArea, JList errorsList, JLabel errorsCountLabel) {
        jTextArea.getDocument().addDocumentListener(this);
        this.jTextArea = jTextArea;
        this.compareToTextArea = compareToTextArea;
        this.errorsList = errorsList;
        this.errorsCountLabel = errorsCountLabel;
        this.mismatchesInStrings = new LinkedList<Integer>();
        this.errorsList.addListSelectionListener(this);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.compareInput();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.compareInput();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.compareInput();
    }

    /**
     * Palyginiamas dviejų teksto laukų turinys. Kas kartą, kai pasikeičia tekstas.
     */
    private void compareInput() {
        // Randame nesutapimų indeksus
        mismatchesInStrings = this.getMismatchesInStrings(
                jTextArea.getText(),
                compareToTextArea.getText()
        );

        // Išvalome pažymėjimus
        this.clearHighlight(jTextArea);
        this.clearHighlight(compareToTextArea);

        // Pažymime klaidas
        highlightMismatches(jTextArea, -1);
        // Sąraše atvaizduojame klaidų indeksus
        this.errorsList.setListData(mismatchesInStrings.toArray());
        // Parodome klaidų skaičių
        this.errorsCountLabel.setText(String.valueOf(mismatchesInStrings.size()));
    }

    /**
     * Suranda nesutapimus dviejose tekstinėse eilutėse
     *
     * @param initial kokią eilutę lyginame
     * @param compareTo su kokia eilute lyginame
     * @return nesutampančių pozicijų sąrašas
     */
    private LinkedList<Integer> getMismatchesInStrings(String initial, String compareTo) {
        LinkedList<Integer> mismatchPositions = new LinkedList<Integer>();

        // Surandame, kuri eilutė trumpiausia (reikalinga, jei ne vienodo ilgio eilutės)
        int shortestLength;
        if (initial.length() < compareTo.length()) {
            shortestLength = initial.length();
        } else {
            shortestLength = compareTo.length();
        }

        for (int i = 0; i < shortestLength; i++) {
            if (initial.charAt(i) != compareTo.charAt(i)) {
                // Rastas nesutapimas. Išsaugome į sąrašą indeksą.
                mismatchPositions.add(i);
            }
        }

        return mismatchPositions;
    }

    /**
     * Paryškina simbolius tekstiniame lauke su nurodytais indeksais
     *
     * @param textArea teksto laukas, kurio simboliai bus paryškinami
     * @param skipIndex kurio simbolio neryškinti (reikalinga, nes jį reikės paryškinti kita spalva, kai bus pasirinkta
     *                  pozicija iš klaidų sąrašo)
     */
    private void highlightMismatches(JTextArea textArea, int skipIndex) {
        for (int i = 0; i < mismatchesInStrings.size(); i++) {
            int index = mismatchesInStrings.get(i);
            if ((index != skipIndex) || (skipIndex < 0)) {
                this.highlightSingleCharacter(Color.cyan, textArea, index);
            }
        }
    }

    /**
     * Kviečiamas, kai klaidų sąraše pasirenkamas indeksas
     *
     * @param e
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedIndex = errorsList.getSelectedIndex();
            if (selectedIndex >= 0) {
                int index = mismatchesInStrings.get(selectedIndex);

                this.clearHighlight(jTextArea);
                this.clearHighlight(compareToTextArea);
                highlightMismatches(jTextArea, index);

                // Paryškiname abiejuose laukuose pasirinktą simbolį
                highlightSingleCharacter(Color.GREEN, jTextArea, index);
                highlightSingleCharacter(Color.GREEN, compareToTextArea, index);
            }
        }
    }

    /**
     * Paryškina vieną simbolį testo lauke
     * @param color spalva
     * @param jTextArea teksto laukas
     * @param index simbolio pozicija
     */
    private void highlightSingleCharacter(Color color, JTextArea jTextArea, int index) {
        try {
            jTextArea.getHighlighter().addHighlight(
                    index,
                    index+1,
                    new DefaultHighlighter.DefaultHighlightPainter(color)
            );
        } catch (BadLocationException e1) {}
    }

    /**
     * Nuima visus paryškinimus nuo teksto lauko
     *
     * @param jTextArea teksto laukas
     */
    private void clearHighlight(JTextArea jTextArea) {
        Highlighter highlighter = jTextArea.getHighlighter();
        Highlighter.Highlight[] highlights = highlighter.getHighlights();

        for (int i = 0; i < highlights.length; i++) {
            if (highlights[i].getPainter() instanceof DefaultHighlighter.DefaultHighlightPainter) {
                highlighter.removeHighlight(highlights[i]);
            }
        }
    }

}

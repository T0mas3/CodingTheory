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

public class VectorTextAreaCompareListener implements DocumentListener, ListSelectionListener {

    private final JTextArea compareToTextArea;
    private final JList errorsList;
    private final JLabel errorsCountLabel;
    JTextArea jTextArea;
    LinkedList<Integer> mismatchesInStrings;

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

    private void compareInput() {
        mismatchesInStrings = this.getMismatchesInStrings(
                jTextArea.getText(),
                compareToTextArea.getText()
        );

        this.clearHighlight(jTextArea);
        this.clearHighlight(compareToTextArea);

        highlightMismatches(jTextArea, -1);
        this.errorsList.setListData(mismatchesInStrings.toArray());
        this.errorsCountLabel.setText(String.valueOf(mismatchesInStrings.size()));
    }

    private LinkedList<Integer> getMismatchesInStrings(String initial, String compareTo) {
        LinkedList<Integer> mismatchPositions = new LinkedList<Integer>();

        int shortestLength;
        if (initial.length() < compareTo.length()) {
            shortestLength = initial.length();
        } else {
            shortestLength = compareTo.length();
        }

        for (int i = 0; i < shortestLength; i++) {
            if (initial.charAt(i) != compareTo.charAt(i)) {
                mismatchPositions.add(i);
            }
        }

        return mismatchPositions;
    }

    private void highlightMismatches(JTextArea textArea, int skipIndex) {
        for (int i = 0; i < mismatchesInStrings.size(); i++) {
            int index = mismatchesInStrings.get(i);
            if ((index != skipIndex) || (skipIndex < 0)) {
                this.highlightSingleCharacter(Color.cyan, textArea, index);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedIndex = errorsList.getSelectedIndex();
            if (selectedIndex >= 0) {
                int index = mismatchesInStrings.get(selectedIndex);

                this.clearHighlight(jTextArea);
                this.clearHighlight(compareToTextArea);
                highlightMismatches(jTextArea, index);

                highlightSingleCharacter(Color.GREEN, jTextArea, index);
                highlightSingleCharacter(Color.GREEN, compareToTextArea, index);
            }
        }
    }

    private void highlightSingleCharacter(Color color, JTextArea jTextArea, int index) {
        try {
            jTextArea.getHighlighter().addHighlight(
                    index,
                    index+1,
                    new DefaultHighlighter.DefaultHighlightPainter(color)
            );
        } catch (BadLocationException e1) {}
    }

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

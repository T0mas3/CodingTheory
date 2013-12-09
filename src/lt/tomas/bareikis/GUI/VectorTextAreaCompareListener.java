package lt.tomas.bareikis.GUI;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.util.LinkedList;

public class VectorTextAreaCompareListener implements DocumentListener {

    private final JTextArea compareToTextArea;
    private final JList errorsList;
    private final JLabel errorsCountLabel;
    JTextArea jTextArea;

    public VectorTextAreaCompareListener(JTextArea jTextArea, JTextArea compareToTextArea, JList errorsList, JLabel errorsCountLabel) {
        jTextArea.getDocument().addDocumentListener(this);
        this.jTextArea = jTextArea;
        this.compareToTextArea = compareToTextArea;
        this.errorsList = errorsList;
        this.errorsCountLabel = errorsCountLabel;
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
        LinkedList<Integer> mismatchesInStrings = this.getMismatchesInStrings(
                jTextArea.getText(),
                compareToTextArea.getText()
        );

        Helper.clearMarkers(jTextArea);
        Helper.clearMarkers(compareToTextArea);

        for (int i = 0; i < mismatchesInStrings.size(); i++) {
            try {
                jTextArea.getHighlighter().addHighlight(
                        mismatchesInStrings.get(i),
                        mismatchesInStrings.get(i)+1,
                        new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN)
                );
            } catch (BadLocationException e1) {}
        }

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

}

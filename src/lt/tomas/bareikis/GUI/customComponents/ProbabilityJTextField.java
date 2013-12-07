package lt.tomas.bareikis.GUI.customComponents;

import lt.tomas.bareikis.GUI.Helper;

import javax.swing.*;
import java.text.ParseException;

public class ProbabilityJTextField extends JTextField {

    public boolean isInputValid() {
        return Helper.isProbabilityValid(this.getText());
    }

    public float getErrorProbability() {
        try {
            float errorProbability = Helper.readInputAsProbabilityFloat(this.getText());
            return errorProbability;
        } catch (ParseException e1) {
            JOptionPane.showMessageDialog(null, "Blogas tikimybės formatas. Tikimybė turi būti intervale [0, 1]");
        } catch (IllegalArgumentException e2) {
            JOptionPane.showMessageDialog(null, "Blogas tikimybės formatas. Tikimybė turi būti intervale [0, 1]");
        }

        return 0;
    }
}

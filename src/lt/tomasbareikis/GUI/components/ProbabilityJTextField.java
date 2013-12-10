package lt.tomasbareikis.GUI.components;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Laukas klasidos tikimybei įvesti. Tikimybė gali būti tik intervale [0, 1]
 */
public class ProbabilityJTextField extends JTextField {

    public boolean isInputValid() {
        return this.isProbabilityValid(this.getText());
    }

    public float getErrorProbability() {
        try {
            float errorProbability = this.readInputAsProbabilityFloat(this.getText());
            return errorProbability;
        } catch (ParseException e1) {
            JOptionPane.showMessageDialog(null, "Blogas tikimybės formatas. Tikimybė turi būti intervale [0, 1]");
        } catch (IllegalArgumentException e2) {
            JOptionPane.showMessageDialog(null, "Blogas tikimybės formatas. Tikimybė turi būti intervale [0, 1]");
        }

        return 0;
    }

    private float readInputAsProbabilityFloat(String inputString) throws ParseException, IllegalArgumentException {
        // Išmetame visus tarpus priekyje ir gale
        inputString = inputString.trim();

        if (!this.isProbabilityValid(inputString)) {
            throw new IllegalArgumentException("Blogas tikimybės formatas. Tikimybė turi būti intervale [0, 1]");
        }

        // Pakeičiame kablelius taškais, kad išvengtume klaidų verčiant į slankaus kablelio skaičių
        inputString = inputString.replace(",", ".");

        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        // Slankaus kablelio skaičiaus sveikoji dalis nuo trupmeninės bus atskiriama tašku
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        Number parsedNumber = decimalFormat.parse(inputString);
        float result = parsedNumber.floatValue();

        if ((result <= 1.0f) && (result >= 0.0f)) {
            return result;
        } else {
            throw new IllegalArgumentException("Blogas tikimybės formatas. Tikimybė turi būti intervale [0, 1]");
        }
    }

    /**
     * Patikrina, ar įvesta tikimybė yra teisinga.
     * Reguliari išraiška patikrina, ar įvesti skaičiai yra tokio formato:
     *
     * 0[kablelis arba taškas][bet kokio ilgio seka skaitemnų intervale 0 - 9]
     * arba
     * 0
     * arba
     * 1
     * arba
     * 1.0
     *
     * @param input tekstas
     * @return ar įvestas tekstas atitinka tikimybės formatą
     */
    private boolean isProbabilityValid(String input) {
        return input.matches("(0[.,][0-9]+|0|1[.,]0|1)");
    }
}

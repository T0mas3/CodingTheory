package lt.tomas.bareikis.GUI;


import com.sun.javaws.exceptions.InvalidArgumentException;
import lt.tomas.bareikis.Channel;
import lt.tomas.bareikis.DataStream;
import lt.tomas.bareikis.Encoder;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.InputMismatchException;

public class Helper {

    public static String encodeVectorString(String vector) {
        Encoder encoder = new Encoder();
        DataStream encodedStream = encoder.encodeForSending(new DataStream(vector));
        return encodedStream.toStringOfBytes();
    }

    public static String transferVectorString(String vector, float errorProbability) {
        Channel channel = new Channel(errorProbability);

        DataStream encodedStream = new DataStream(vector);
        DataStream transferredStream = channel.transfer(encodedStream);
        return transferredStream.toStringOfBytes();
    }

    public static boolean isVectorInputValid(String input) {
        return input.matches("[0-1]*");
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
    public static boolean isProbabilityalid(String input) {
        return input.matches("(0[.,][0-9]+|0|1[.,]0|1)");
    }

    public static float readInputAsFloat(String inputString) throws ParseException, IllegalArgumentException {
        // Išmetame visus tarpus priekyje ir gale
        inputString = inputString.trim();

        if (!Helper.isProbabilityalid(inputString)) {
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

}

package lt.tomas.bareikis.GUI;


import lt.tomas.bareikis.Channel;
import lt.tomas.bareikis.DataStream;
import lt.tomas.bareikis.Decoder;
import lt.tomas.bareikis.Encoder;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

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

    public static String decodeVectorString(String vector) {
        DataStream transferredStream = new DataStream(vector);
        Decoder decoder = new Decoder();
        DataStream decodedStream = decoder.decodeAfterReceiving(transferredStream);

        return decodedStream.toStringOfBytes();
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
    public static boolean isProbabilityValid(String input) {
        return input.matches("(0[.,][0-9]+|0|1[.,]0|1)");
    }

    public static float readInputAsProbabilityFloat(String inputString) throws ParseException, IllegalArgumentException {
        // Išmetame visus tarpus priekyje ir gale
        inputString = inputString.trim();

        if (!Helper.isProbabilityValid(inputString)) {
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

    public static String transformStringToBitString(String input) {

        String bitString = "";

        int requiredLength = 8;

        for (int i = 0; i < input.length(); i++) {
            String singleCharBitString = Integer.toBinaryString((int)input.charAt(i));

            if (singleCharBitString.length() < requiredLength) {
                for (int n = 0; n < requiredLength - singleCharBitString.length(); n++) {
                    singleCharBitString = "0" + singleCharBitString;
                }
            }

            bitString += singleCharBitString;
        }

        return bitString;
    }

    public static String transformBitStringToCharactersString(String input) {

        String charactersString = "";

        for (int i = 0; i < input.length() / 8; i++) {
            int characterInt = Integer.parseInt(input.substring(i*8, (i*8) + 8), 2);
            charactersString = charactersString + (char)characterInt;
        }

        return charactersString;
    }

}

package lt.tomas.bareikis.GUI;


import lt.tomas.bareikis.Channel;
import lt.tomas.bareikis.DataStream;
import lt.tomas.bareikis.Decoder;
import lt.tomas.bareikis.Encoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.LinkedList;

public class Helper {

    public static final int CHAR_BIT_LEN = 16;

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

        int requiredLength = CHAR_BIT_LEN;

        for (int i = 0; i < input.length(); i++) {
            String singleCharBitString = Integer.toBinaryString((int)input.charAt(i));
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

    public static String transformBitStringToCharactersString(String input) {

        String charactersString = "";

        for (int i = 0; i < input.length() / CHAR_BIT_LEN; i++) {
            int characterInt = Integer.parseInt(input.substring(i*CHAR_BIT_LEN, (i*CHAR_BIT_LEN) + CHAR_BIT_LEN), 2);
            charactersString = charactersString + (char)characterInt;
        }

        return charactersString;
    }


    public static String transformImageFileToBitString(File imageFile, String imageFormat) throws IllegalArgumentException, IOException {

        imageFormat = imageFormat.toLowerCase();

        if ((imageFormat.equals("jpg")) || (imageFormat.equals("png"))) {

            byte[] imgBytes = Files.readAllBytes(Paths.get(imageFile.getPath()));

            return getStringFromByteArray(imgBytes);
        } else {
            throw new IllegalArgumentException("Netinkamas failo formatas. Turi būti jpg arba png.");
        }
    }

    public static byte[] transformBitStringToBitArray(String bytesString) throws IllegalArgumentException, IOException {

        byte[] fileBytes = new byte[bytesString.length() / 8];

        for (int i = 0; i < bytesString.length() / 8; i++) {
            String byteString = bytesString.substring(i*8, (i*8)+8);
            fileBytes[i] = (byte) Integer.parseInt(byteString, 2);
        }

        return fileBytes;
    }

    public static String transformImageToBitString(BufferedImage bufferedImage, String imageFormat) throws IllegalArgumentException, IOException {

        imageFormat = imageFormat.toLowerCase();

        if ((imageFormat.equals("jpg")) || (imageFormat.equals("png"))) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, imageFormat, byteArrayOutputStream);

            int[] colors = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
            bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), colors, 0, bufferedImage.getWidth());

            byte[] rgbImage = new byte[colors.length*3];

            for (int i = 0; i < colors.length; i++) {
                Color color = new Color(colors[i]);
                rgbImage[i*3] = (byte)color.getRed();
                rgbImage[i*3+1] = (byte)color.getGreen();
                rgbImage[i*3+2] = (byte)color.getBlue();
            }

            return getStringFromByteArray(rgbImage);
        } else {
            throw new IllegalArgumentException("Netinkamas failo formatas. Turi būti jpg arba png.");
        }
    }

    public static String getStringFromByteArray(byte[] bytes) {

        StringBuilder stringBuilder = new StringBuilder();

        for (byte imgByte: bytes) {
            String singleByteString = Integer.toBinaryString(imgByte & 0xFF);

            int addZeroes = 8 - singleByteString.length();
            for (int n = 0; n < addZeroes; n++) {
                singleByteString = "0" + singleByteString;
            }
            stringBuilder.append(singleByteString);
        }
        return stringBuilder.toString();
    }


    public static void clearMarkers(JTextArea jTextArea) {
        Highlighter highlighter = jTextArea.getHighlighter();
        Highlighter.Highlight[] highlights = highlighter.getHighlights();

        for (int i = 0; i < highlights.length; i++) {
            if (highlights[i].getPainter() instanceof DefaultHighlighter.DefaultHighlightPainter) {
                highlighter.removeHighlight(highlights[i]);
            }
        }
    }


}

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

    public static String transformBitStringToCharactersString(String input) {

        String charactersString = "";
        int charBitLen = 16;

        for (int i = 0; i < input.length() / charBitLen; i++) {
            int characterInt = Integer.parseInt(input.substring(i*charBitLen, (i*charBitLen) + charBitLen), 2);
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

}

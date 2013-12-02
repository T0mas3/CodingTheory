package lt.tomas.bareikis.GUI;


import lt.tomas.bareikis.DataStream;
import lt.tomas.bareikis.Encoder;

public class Helper {

    public static String encodeVectorString(String vector) {
        Encoder encoder = new Encoder();
        DataStream encodedStream = encoder.encodeForSending(new DataStream(vector));
        return encodedStream.toStringOfBytes();
    }

    public static boolean isInputValid(String input) {
        return input.matches("[0-1]*");
    }

}

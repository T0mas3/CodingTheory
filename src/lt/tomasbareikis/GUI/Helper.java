package lt.tomasbareikis.GUI;


import lt.tomasbareikis.Channel;
import lt.tomasbareikis.DataStream;
import lt.tomasbareikis.Decoder;
import lt.tomasbareikis.Encoder;

/**
 * Pagalbinės funkcijos
 */
public class Helper {

    /**
     *
     * Užkoduoja vektorių, paduotą kaip tekstinė eilutė
     *
     * @param vector vektorius tekstinės eilutės pavidalu
     * @return užkoduotas vektorius tekstinės eilutės pavidalu
     */
    public static String encodeVectorString(String vector) {
        Encoder encoder = new Encoder();
        DataStream encodedStream = encoder.encodeForSending(new DataStream(vector));
        return encodedStream.toStringOfBytes();
    }

    /**
     * Persiunčia vektorių kanalu ir jį iškraipo pagal klaidų tikimybę
     *
     * @param vector vektorius tekstinės eilutės pavidalu
     * @param errorProbability klaidos tikimybė intervale [0, 1]
     * @return vektorius, persiųstas kanalu
     */
    public static String transferVectorString(String vector, float errorProbability) {
        Channel channel = new Channel(errorProbability);

        DataStream encodedStream = new DataStream(vector);
        DataStream transferredStream = channel.transfer(encodedStream);
        return transferredStream.toStringOfBytes();
    }

    /**
     *  Atkoduoja vektorių
     *
     * @param vector vektorius tekstinės eilutės pavidalu
     * @return atkoduotas vektorius tekstinės eilutės pavidalu
     */
    public static String decodeVectorString(String vector) {
        DataStream transferredStream = new DataStream(vector);
        Decoder decoder = new Decoder();
        DataStream decodedStream = decoder.decodeAfterReceiving(transferredStream);

        return decodedStream.toStringOfBytes();
    }

    /**
     * Paverčia vektorių į simbolių eilutę
     *
     * @param input vektorius tekstinės eilutės pavidalu
     * @return eilutė simbolių, užkoduotų duotame vektoriuje
     */
    public static String transformBitStringToCharactersString(String input) {

        String charactersString = "";
        int charBitLen = 16; // Vienas simbolis koduojamas 16 bitų. Tam, kad būtų galima perduoti Lietuviškus simbolius

        // Įvestą veiktorių skaidome į dalis po 16 bitų ir kiekvieną dalį verčiame į skaičių iš dvejetainės sistemos
        for (int i = 0; i < input.length() / charBitLen; i++) {
            // Paverčiame dalį tekstinės eilutės į skaičių
            int characterInt = Integer.parseInt(input.substring(i*charBitLen, (i*charBitLen) + charBitLen), 2);
            // Skaičių paverčiame simboliu (skaičius atitinka simbolio kodą)
            charactersString = charactersString + (char)characterInt;
        }

        return charactersString;
    }
}

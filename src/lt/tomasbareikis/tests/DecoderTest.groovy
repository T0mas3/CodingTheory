package lt.tomasbareikis.tests

import lt.tomasbareikis.DataStream
import lt.tomasbareikis.Decoder
import lt.tomasbareikis.Encoder
import org.junit.Test

class DecoderTest {

    @Test
    void decodingEmptyWorking() {

        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();

        DataStream input = new DataStream("");
        DataStream encoded = encoder.encodeForSending(input);
        DataStream decoded = decoder.decodeAfterReceiving(encoded);

        assert decoded.equals(input);
    }

    @Test
    void decodingWorking1() {

        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();

        DataStream input = new DataStream("111");
        DataStream encoded = encoder.encodeForSending(input);
        DataStream decoded = decoder.decodeAfterReceiving(encoded);

        assert decoded.equals(input);
    }

    @Test
    void decodingWorking2() {

        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();

        DataStream input = new DataStream("1");
        DataStream encoded = encoder.encodeForSending(input);
        DataStream decoded = decoder.decodeAfterReceiving(encoded);

        assert decoded.equals(input);
    }

    @Test
    void decodingWorking3() {

        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();

        DataStream input = new DataStream("0");
        DataStream encoded = encoder.encodeForSending(input);
        DataStream decoded = decoder.decodeAfterReceiving(encoded);

        assert decoded.equals(input);
    }

    @Test
    void decodingWorking4() {

        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();

        DataStream input = new DataStream("10101010101");
        DataStream encoded = encoder.encodeForSending(input);
        DataStream decoded = decoder.decodeAfterReceiving(encoded);

        assert decoded.equals(input);
    }


    @Test
    void decodingLongWorking1() {

        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();

        String inputStr =
                "1011111111001000111101010011011111010110000111010011011101001110" +
                "0010000101111011110010100101111011001101111010011101010001010110" +
                "0100011001100111000001011010000100111110110111110011000111001000" +
                "0001111110100000001101010000100001100000110001100111000101110010" +
                "0000100011011110111111101111100010001111111010101011100101111010";

        DataStream input = new DataStream(inputStr);
        DataStream encoded = encoder.encodeForSending(input);
        DataStream decoded = decoder.decodeAfterReceiving(encoded);

        assert decoded.equals(input);
    }

}


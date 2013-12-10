package lt.tomasbareikis.tests

import lt.tomasbareikis.DataStream
import lt.tomasbareikis.Encoder
import org.junit.Test

class EncoderTest {

    @Test
    void encodingSingleBitWorking1() {

        Encoder encoder = new Encoder();

        DataStream result = encoder.encode(new DataStream("1"));

        assert result.equals(new DataStream("11"));
    }

    @Test
    void encodingSingleBitWorking2() {

        Encoder encoder = new Encoder();

        DataStream result = encoder.encode(new DataStream("0"));

        assert result.equals(new DataStream("00"));
    }

    @Test
    void encodingNoneIsWorking() {

        Encoder encoder = new Encoder();

        DataStream result = encoder.encode(new DataStream());

        assert result.equals(new DataStream());
    }

    @Test
    void encodingSingleBitRegistersWorking1() {

        Encoder encoder = new Encoder();

        encoder.encode(new DataStream("1"));

        assert encoder.getRegistersValues().equals(new DataStream("100000"));
    }

    @Test
    void encodingSingleBitRegistersWorking2() {

        Encoder encoder = new Encoder();

        encoder.encode(new DataStream("0"));

        assert encoder.getRegistersValues().equals(new DataStream("000000"));
    }

    @Test
    void encodingRegistersWorking1() {

        Encoder encoder = new Encoder();

        encoder.encode(new DataStream("111"));

        assert encoder.getRegistersValues().equals(new DataStream("111000"));
    }

    @Test
    void encodingRegistersWorking2() {

        Encoder encoder = new Encoder();

        encoder.encode(new DataStream("111111"));

        assert encoder.getRegistersValues().equals(new DataStream("111111"));
    }

    @Test
    void fullEncodingWorking1() {
        Encoder encoder = new Encoder();
        DataStream input = new DataStream("1");
        assert encoder.encodeForSending(input).equals(new DataStream("11000100000101"));
    }

    @Test
    void fullEncodingWorking2() {
        Encoder encoder = new Encoder();
        DataStream input = new DataStream("0");
        assert encoder.encodeForSending(input).equals(new DataStream("00000000000000"));
    }

}

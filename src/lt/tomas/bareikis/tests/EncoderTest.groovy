package lt.tomas.bareikis.tests

import lt.tomas.bareikis.DataStream
import lt.tomas.bareikis.Encoder
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

}

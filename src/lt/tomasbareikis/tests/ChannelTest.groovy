package lt.tomasbareikis.tests

import lt.tomasbareikis.Channel
import lt.tomasbareikis.DataStream
import org.junit.Test

class ChannelTest {

    @Test
    void transferWorkingWithZeroErrorProbability1() {

        Channel channel = new Channel(0);
        DataStream input = new DataStream("1111111111");
        DataStream result = channel.transfer(input);

        assert result.equals(input);
    }

    @Test
    void transferWorkingWithZeroErrorProbability2() {

        Channel channel = new Channel(0);
        DataStream input = new DataStream("0000000000");
        DataStream result = channel.transfer(input);
        assert result.equals(input);
    }

    @Test
    void transferWorkingWithZeroErrorProbability3() {

        Channel channel = new Channel(0);
        DataStream input = new DataStream("101");
        DataStream result = channel.transfer(input);
        assert result.equals(input);
    }

    @Test
    void transferWorkingWithOneErrorProbability1() {

        Channel channel = new Channel(1);
        DataStream input = new DataStream("1111111111");
        DataStream result = channel.transfer(input);

        assert result.equals(new DataStream("0000000000"));
    }

    @Test
    void transferWorkingWithOneErrorProbability2() {

        Channel channel = new Channel(1);
        DataStream input = new DataStream("10101011");
        DataStream result = channel.transfer(input);

        assert result.equals(new DataStream("01010100"));
    }

}

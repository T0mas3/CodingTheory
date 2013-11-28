package lt.tomas.bareikis.tests

import lt.tomas.bareikis.Bit
import lt.tomas.bareikis.DataStream
import org.junit.Test

class DataStreamTest {

    @Test
    void appendToEndWorking1() {
        DataStream dataStream = new DataStream();
        dataStream.appendToStreamEnd(new Bit(1));

        assert dataStream.getDataAt(0).getValue() == 1;
    }

    @Test
    void appendToEndWorking2() {
        DataStream dataStream = new DataStream();
        dataStream.appendToStreamEnd(new Bit(0));
        dataStream.appendToStreamEnd(new Bit(0));
        dataStream.appendToStreamEnd(new Bit(1));
        dataStream.appendToStreamEnd(new Bit(0));
        dataStream.appendToStreamEnd(new Bit(0));
        dataStream.appendToStreamEnd(new Bit(1));

        assert dataStream.getDataAt(dataStream.getSize()-1).getValue() == 1;
    }

    @Test
    void crateFromStringWorking1() {
        DataStream dataStream = new DataStream("100110");

        assert dataStream.getDataAt(0).getValue() == 1;
        assert dataStream.getDataAt(1).getValue() == 0;
        assert dataStream.getDataAt(2).getValue() == 0;
        assert dataStream.getDataAt(3).getValue() == 1;
        assert dataStream.getDataAt(4).getValue() == 1;
        assert dataStream.getDataAt(5).getValue() == 0;
    }

    @Test
    void crateFromStringWorking2() {
        DataStream dataStream = new DataStream("0");

        assert dataStream.getDataAt(0).getValue() == 0;
    }

    @Test
    void crateFromStringWorking3() {
        DataStream dataStream = new DataStream("1");

        assert dataStream.getDataAt(0).getValue() == 1;
    }

    @Test
    void crateFromStringWorking4() {
        DataStream dataStream = new DataStream("");

        assert dataStream.getSize() == 0;
    }

}

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


}

package lt.tomas.bareikis;

import java.util.Random;

public class Channel {

    float errorProbability = 0;

    public Channel() {
        errorProbability = 0;
    }

    public Channel(float errorProbability) {
        this.errorProbability = errorProbability;
    }

    public DataStream transfer(DataStream dataStream) {

        DataStream resultDataStream = new DataStream();

        for (Bit bit: dataStream.getDataAsBitList()) {
            resultDataStream.appendToStreamEnd(this.transferSingleBit(bit));
        }

        return resultDataStream;
    }

    private Bit transferSingleBit(Bit bit) {
        Bit outBit = new Bit(bit);
        if (isErrorOccurred()) {
            outBit.add(1);
        }

        return outBit;
    }

    public boolean isErrorOccurred() {
        Random rand = new Random();

        return rand.nextFloat() <= errorProbability;
    }

    public float getErrorProbability() {
        return errorProbability;
    }

    public void setErrorProbability(float errorProbability) {
        this.errorProbability = errorProbability;
    }
}

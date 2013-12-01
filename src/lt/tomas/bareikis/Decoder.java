package lt.tomas.bareikis;

public class Decoder {

    protected CommonRegisterGroup commonRegisterGroup;
    protected DecoderRegisterGroup decoderRegisterGroup;
    protected MDE mde;


    public Decoder() {
        commonRegisterGroup = new CommonRegisterGroup();
        decoderRegisterGroup = new DecoderRegisterGroup();
        mde = new MDE();
    }

    public DataStream decodeAfterSending(DataStream encodedDataStream) {

        DataStream decodedStream = this.decode(encodedDataStream);

        return decodedStream;
    }

    public DataStream decode(DataStream encodedDataStream) {

        DataStream decodedStream = new DataStream();

        int i = 0;

        while (i < encodedDataStream.getSize()-1) {
            decodedStream.appendToStreamEnd(
                    this.decodeBits(encodedDataStream.getDataAt(i), encodedDataStream.getDataAt(i + 1))
            );
            i+= 2;
        }

        return decodedStream;
    }

    public DataStream decodeForReceiving(DataStream dataStream) {
        DataStream decodedDataStream = this.decode(dataStream);
        return decodedDataStream.getSubStream(6, decodedDataStream.getSize());
    }

    public Bit decodeBits(Bit inputBit1, Bit inputBit2) {

        Bit outputBit2 = this.decodeSecondBit(inputBit1, inputBit2);
        Bit outputBit1 = this.decodeFirstBit(inputBit1);

        outputBit1.add(outputBit2);

        return new Bit(outputBit1);
    }

    private Bit decodeSecondBit(Bit inputBit1, Bit inputBit2) {

        Bit firstBitToMDE = new Bit(inputBit1);

        firstBitToMDE.add(inputBit2);
        firstBitToMDE.add(commonRegisterGroup.getRegisterValueAt(1));
        firstBitToMDE.add(commonRegisterGroup.getRegisterValueAt(4));
        firstBitToMDE.add(commonRegisterGroup.getRegisterValueAt(5));

        DataStream toMDE = new DataStream();
        toMDE.appendToStreamEnd(firstBitToMDE);
        toMDE.appendToStreamEnd(decoderRegisterGroup.getRegisterValueAt(0));
        toMDE.appendToStreamEnd(decoderRegisterGroup.getRegisterValueAt(3));
        toMDE.appendToStreamEnd(decoderRegisterGroup.getRegisterValueAt(5));

        Bit result = mde.calculate(toMDE);

        decoderRegisterGroup.shiftRegisters(firstBitToMDE, result);

        return result;
    }

    private Bit decodeFirstBit(Bit inputBit) {

        Bit result = new Bit(commonRegisterGroup.getLastRegisterValue());
        commonRegisterGroup.shiftRegisters(inputBit);

        return result;
    }

    public String getRegistersDump() {
        return getRegistersValues().toString();
    }

    public DataStream getRegistersValues() {
        return this.commonRegisterGroup.getRegistersValuesAsDataStream();
    }
}

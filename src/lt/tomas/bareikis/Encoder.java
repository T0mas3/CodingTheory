package lt.tomas.bareikis;

public class Encoder {

    protected EncoderRegisterGroup encoderRegisterGroup;


    public Encoder() {
        encoderRegisterGroup = new EncoderRegisterGroup();
    }

    public DataStream encodeForSending(DataStream dataStream) {

        // Užkoduojame paduotus duomenis
        DataStream encodedStream = this.encode(dataStream);
        // Taip pat užkodauojame dar 6 papildomus nulinius bitus, kad supaprastintume dekodavimą.
        encodedStream.appendToStreamEnd(this.encode(new DataStream("000000")));

        return encodedStream;
    }

    public DataStream encode(DataStream dataStream) {

        DataStream encodedStream = new DataStream();

        for (Bit bit: dataStream.getDataAsBitList()) {
            DataStream encodedBitStream = this.encodeSingleBit(bit);
            encodedStream.appendToStreamEnd(encodedBitStream);
        }

        return encodedStream;
    }

    public DataStream encodeSingleBit(Bit inputBit) {

        DataStream output = new DataStream();

        // Į išėjimą keliauja tas pats bitas, kuris ir įėjo į enkoderį
        output.appendToStreamEnd(inputBit);

        // Antrasis bitas, kuris išeis iš dekoderio
        Bit additionalBit = new Bit(inputBit);

        // Atliekam sudėties moduliu 2 operacijsa su atitinkamais registrais
        additionalBit.add(encoderRegisterGroup.getRegisterValueAt(1));
        additionalBit.add(encoderRegisterGroup.getRegisterValueAt(4));
        additionalBit.add(encoderRegisterGroup.getRegisterValueAt(5));

        // Pridedam gautą bitą prie išėjusių bitų sekos
        output.appendToStreamEnd(additionalBit);

        encoderRegisterGroup.shiftRegisters(inputBit);

        return output;
    }

    public String getRegistersDump() {
        return getRegistersValues().toString();
    }

    public DataStream getRegistersValues() {
        return this.encoderRegisterGroup.getRegistersValuesAsDataStream();
    }
}

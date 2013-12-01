package lt.tomas.bareikis;

public class Encoder {

    Register register1,
             register2,
             register3,
             register4,
             register5,
             register6;


    public Encoder() {
        register1 = new Register();
        register2 = new Register();
        register3 = new Register();
        register4 = new Register();
        register5 = new Register();
        register6 = new Register();
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
        additionalBit.add(register2.getValue());
        additionalBit.add(register5.getValue());
        additionalBit.add(register6.getValue());

        // Pridedam gautą bitą prie išėjusių bitų sekos
        output.appendToStreamEnd(additionalBit);

        this.shiftRegisters(inputBit);

        return output;
    }

    /**
     *
     * @param inputBit įėjęs į enkoderį bitas
     */
    private void shiftRegisters(Bit inputBit) {
        // "Pastumame" registrų reikšmes. Kiekvienas registras gauna prieš jį esančio registro reikšmę
        register6.setValue(register5.getValue());
        register5.setValue(register4.getValue());
        register4.setValue(register3.getValue());
        register3.setValue(register2.getValue());
        register2.setValue(register1.getValue());
        // Pirmasis registras gauna įėjusio bito reikšmę
        register1.setValue(inputBit);
    }

    public String getRegistersDump() {
        return getRegistersValues().toString();
    }

    public DataStream getRegistersValues() {
        DataStream result = new DataStream();

        result.appendToStreamEnd(new Bit(register1.getValue()));
        result.appendToStreamEnd(new Bit(register2.getValue()));
        result.appendToStreamEnd(new Bit(register3.getValue()));
        result.appendToStreamEnd(new Bit(register4.getValue()));
        result.appendToStreamEnd(new Bit(register5.getValue()));
        result.appendToStreamEnd(new Bit(register6.getValue()));

        return result;
    }
}

package lt.tomasbareikis;

/**
 * Sąsūkos kodo dekoderis su grįžtamuoju ryšiu.
 */
public class Decoder {

    // Tokia pat registrų grupė kaip ir enkoderyje
    protected CommonRegisterGroup commonRegisterGroup;
    // Dekoderiui specifinė, papildoma registrų grupė
    protected DecoderRegisterGroup decoderRegisterGroup;
    // Majority Decision Element (nustato, kurios iš paduotų reikšmių sekoje yra daugiausia)
    protected MDE mde;


    public Decoder() {
        commonRegisterGroup = new CommonRegisterGroup();
        decoderRegisterGroup = new DecoderRegisterGroup();
        mde = new MDE();
    }

    /**
     * Dekoduoja užkoduotą vektorių
     *
     * @param encodedDataStream užkoduotas duomenų vektorius
     * @return atkoduotas vektorius
     */
    public DataStream decode(DataStream encodedDataStream) {

        DataStream decodedStream = new DataStream();

        int i = 0;

        while (i < encodedDataStream.getSize()-1) {
            // Į dekoderį įeina po du bitus
            decodedStream.appendToStreamEnd(
                    this.decodeBits(encodedDataStream.getDataAt(i), encodedDataStream.getDataAt(i + 1))
            );
            i+= 2;
        }

        return decodedStream;
    }

    /**
     * Dekoduoja gautus duomenis iš kanalo (duomenys su papildoma informacija)
     *
     * @param dataStream duomenys iš kanalo
     * @return atkoduoti duomenys
     */
    public DataStream decodeAfterReceiving(DataStream dataStream) {
        // Atkoduojame duomenis
        DataStream decodedDataStream = this.decode(dataStream);
        // Atmetame pirmus 6 bitus (papildoma informacija padedanti dekoduojant)
        return decodedDataStream.getSubStream(6, decodedDataStream.getSize());
    }

    /**
     * Atliekamas vienas dekodavimo žingsnis (dekoduojami du įėję bitai)
     *
     * @param inputBit1 pirmasis bitas
     * @param inputBit2 antrasis bitas
     * @return dekoduotas bitas
     */
    public Bit decodeBits(Bit inputBit1, Bit inputBit2) {

        // Dekoduojamas antras bitas (kuris eina į schemos dalį su MDE, schemos apatinė dalis)
        Bit outputBit2 = this.decodeSecondBit(inputBit1, inputBit2);
        // Dekoduojamas pirmas bitas (kuris eina į schemos virštinę dalį)
        Bit outputBit1 = this.decodeFirstBit(inputBit1);

        // Abu gautus bitus prieš išeiant XOR'iname
        outputBit1.add(outputBit2);

        return new Bit(outputBit1);
    }

    /**
     * Dekodavimas apatinėje schemos dalyje (su MDE)
     *
     * @param inputBit1 pirmasis bitas
     * @param inputBit2 antrasis bitas
     * @return dekoduotas bitas iš apatinės schemos dalies
     */
    private Bit decodeSecondBit(Bit inputBit1, Bit inputBit2) {

        Bit firstBitToMDE = new Bit(inputBit1);

        // Pirmasis iš 4 bitų į MDE sudaromas iš įeinančio bito ir iš "bendrų registrų" (viršuje) reikšmių
        firstBitToMDE.add(inputBit2);
        firstBitToMDE.add(commonRegisterGroup.getRegisterValueAt(1));
        firstBitToMDE.add(commonRegisterGroup.getRegisterValueAt(4));
        firstBitToMDE.add(commonRegisterGroup.getRegisterValueAt(5));

        // 4 bitai į MDE
        DataStream toMDE = new DataStream();
        toMDE.appendToStreamEnd(firstBitToMDE);
        toMDE.appendToStreamEnd(decoderRegisterGroup.getRegisterValueAt(0));
        toMDE.appendToStreamEnd(decoderRegisterGroup.getRegisterValueAt(3));
        toMDE.appendToStreamEnd(decoderRegisterGroup.getRegisterValueAt(5));

        Bit result = mde.calculate(toMDE);

        // Pastumiame registrų reikšmes
        decoderRegisterGroup.shiftRegisters(firstBitToMDE, result);

        return result;
    }

    /**
     * Dekodavimas biršutinėje schemos dalyje
     *
     * @param inputBit pirmas bitas
     * @return bitas iš viršutinės schemos dalies
     */
    private Bit decodeFirstBit(Bit inputBit) {

        // Paskutinio registro reikšmė išeina
        Bit result = new Bit(commonRegisterGroup.getLastRegisterValue());
        // Perstumiame registrų reikšmes
        commonRegisterGroup.shiftRegisters(inputBit);

        return result;
    }

    /**
     * Grąžina visų reigistrų reikšmės kaip tekstinę eilutę
     *
     * @return visų registro reikšmių rekstinė eilutė
     */
    public String getRegistersDump() {
        return getRegistersValues().toString();
    }

    /**
     * Grąžina visų registrų reikšmes kaip bitų seką.
     *
     * @return visų registrų reikšmės
     */
    public DataStream getRegistersValues() {
        return this.commonRegisterGroup.getRegistersValuesAsDataStream();
    }
}

package lt.tomas.bareikis;

/**
 * Uždokuoja duotą vektorių sąsūkos kodu.
 */
public class Encoder {

    // Enkoderio registrų grupė
    protected CommonRegisterGroup commonRegisterGroup;


    /**
     * Sukuria naują enkoderį
     */
    public Encoder() {
        commonRegisterGroup = new CommonRegisterGroup();
    }

    /**
     * Užkoduojamas vektorius, paruoštas siuntimui. Skiriasi nuo paprasto vektoriaus kodavimo, kad pridedami papildomi
     * duomenys, tam, kad dekodavimas būtų paprastesnis (žinotume, kada baigti dekoduoti)
     *
     * @param dataStream bitų seka (vektorius), kuris bus koduojamas
     * @return užkoduotas vektorius, paruoštas siuntimui
     */
    public DataStream encodeForSending(DataStream dataStream) {

        // Užkoduojame paduotus duomenis
        DataStream encodedStream = this.encode(dataStream);
        // Taip pat užkodauojame dar 6 papildomus nulinius bitus, kad supaprastintume dekodavimą.
        encodedStream.appendToStreamEnd(this.encode(new DataStream("000000")));

        return encodedStream;
    }

    /**
     * Užkoduojamas pradinis vektorius. Be papildomos informacijos.
     *
     * @param dataStream bitų seka (vektorius), kuris bus koduojamas
     * @return užkoduotas vektorius
     */
    public DataStream encode(DataStream dataStream) {

        DataStream encodedStream = new DataStream();

        // Užkoduojame po vieną bitą ir jungiame prie rezultato
        for (Bit bit: dataStream.getDataAsBitList()) {
            DataStream encodedBitStream = this.encodeSingleBit(bit);
            encodedStream.appendToStreamEnd(encodedBitStream);
        }

        return encodedStream;
    }

    /**
     * Užkoduoja vieną bitą
     *
     * @param inputBit koduojamas bitas
     * @return bitų seka, gauta užkodavus vieną bitą
     */
    public DataStream encodeSingleBit(Bit inputBit) {

        DataStream output = new DataStream();

        // Į išėjimą keliauja tas pats bitas, kuris ir įėjo į enkoderį
        output.appendToStreamEnd(inputBit);

        // Antrasis bitas, kuris išeis iš dekoderio
        Bit additionalBit = new Bit(inputBit);

        // Atliekam sudėties moduliu 2 operacijsa su atitinkamais registrais
        additionalBit.add(commonRegisterGroup.getRegisterValueAt(1));
        additionalBit.add(commonRegisterGroup.getRegisterValueAt(4));
        additionalBit.add(commonRegisterGroup.getRegisterValueAt(5));

        // Pridedam gautą bitą prie išėjusių bitų sekos
        output.appendToStreamEnd(additionalBit);

        // Pastumiame visų registrų reikšmes
        commonRegisterGroup.shiftRegisters(inputBit);

        return output;
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

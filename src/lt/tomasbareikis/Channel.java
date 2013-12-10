package lt.tomasbareikis;

import java.util.Random;

/**
 * Duomenų perdavimo kanalas. Gali iškraipydi duomenis.
 */
public class Channel {

    // Tikimybė, kad perduodant vieną bitą kanalu įvyks klaida.
    // Tikimybė nurodoma intervale nuo 0 iki 1
    float errorProbability = 0;

    /**
     * Sukuria kanalą su klaidos tikimybe lygia 0
     */
    public Channel() {
        errorProbability = 0;
    }

    /**
     *
     * Sukuria kanalą su nurodyta klaidos tikimybe
     *
     * @param errorProbability klaidos tikimybė
     */
    public Channel(float errorProbability) {
        this.errorProbability = errorProbability;
    }

    /**
     * Perduoda duomenis kanalu
     *
     * @param dataStream duomenys
     * @return duomenys išėje iš kanalo
     */
    public DataStream transfer(DataStream dataStream) {

        DataStream resultDataStream = new DataStream();

        for (Bit bit: dataStream.getDataAsBitList()) {
            // Perduodame po vieną bitą ir pridedame prie rezultato
            resultDataStream.appendToStreamEnd(this.transferSingleBit(bit));
        }

        return resultDataStream;
    }

    /**
     * Perduoda vieną bitą kanalu, atsižvelgiant į nustatytą iškraipymo tikimybę.
     * @param bit perduodamas bitas
     * @return iš kanalo išėjęs bitas
     */
    private Bit transferSingleBit(Bit bit) {
        Bit outBit = new Bit(bit);
        if (isErrorOccurred()) {
            // Jei įvyko klaida, bto reikšmę pakeičiame tiesiog prie jos pridėdami 1. Tada 0 virs 1 ir atvirkščiai.
            outBit.add(1);
        }

        return outBit;
    }

    /**
     * Sugeneruojamas atsitiktinis skaičius ir pagal jį nustatoma ar kanale turėtų įvykti klaida.
     *
     * @return ar įvyko klaida
     */
    public boolean isErrorOccurred() {
        Random rand = new Random();

        // Laikome, kad klaida įvyksta tada, kai sugeneruotas atsisiktinis skaičius yra mežesnis nei kad klaidos tikimybė.
        return rand.nextFloat() <= errorProbability;
    }

    /**
     *
     * Grąžina klaidos tikimynbę
     *
     * @return klaidos tikimybė
     */
    public float getErrorProbability() {
        return errorProbability;
    }

    /**
     * Nustato naują klaidos tikimybę
     *
     * @param errorProbability klaidos tikimybė
     */
    public void setErrorProbability(float errorProbability) {
        this.errorProbability = errorProbability;
    }
}

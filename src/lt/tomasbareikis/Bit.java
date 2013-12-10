package lt.tomasbareikis;

/**
 * Klasė apibrėžia vieną bitą. Gali būti naudojama saugoti reikšmes ir kitokias nei kad 0 ir 1. Kokiu moduliu bus skaičiuojama
 * gali būti nurodyta lauke "modulus"
 */
public class Bit {

    int modulus = 2;
    int value = 0;

    /**
     * Sukuria bitą su tokia pat reikšme kaip ir paduoto bito
     *
     * @param bit reikšmė, kurią turės įgauti naujas bitas
     */
    public Bit(Bit bit) {
        this.modulus = bit.getModulus();
        this.value = bit.getValue();
    }

    /**
     * Sukuriamas naujas bitas su nurodyta reikšme. Jei ji yra visada saugoma nurodytu moduliu.
     * T.y., jei paduodama reikšmė 5, o modulis yra 2, taio išsaugoma reikšmė 1.
     *
     * @param modulus kokiu moduliu saugomos reikšmės
     * @param value reikšmė
     */
    public Bit(int modulus, int value) {
        this.modulus = modulus;
        this.value = value % modulus;
    }

    /**
     * Sukuria naują bitą su nurodyta reikšme. Naudojamas modulis 2.
     *
     * @param value reikšmė
     */
    public Bit(int value) {
        this.value = value % modulus;
    }

    /**
     * Sukuria naują bitą pagal nurodyta simboline reikšmę. Naudojamas modulis 2.
     *
     * @param charValue simbolinė reikšmė
     * @throws IllegalArgumentException
     */
    public Bit(char charValue) throws IllegalArgumentException {

        int intValue = Character.getNumericValue(charValue); // Bandome gauti skaitinę reikšmę
        if (intValue == -1) {
            throw new IllegalArgumentException("Netinkamas simbolis");
        }

        this.value = intValue % modulus; // Reikšmę saugome nurodytu moduliu
    }

    /**
     * Sukuria naują bitą su standartinėmis pradinėmis reikšmėmis.
     * Naudojamas modulis 2. Reikšmė 0.
     */
    public Bit() {}

    /**
     * Grąžina bito reikšmę
     *
     * @return bito reikšmė
     */
    public int getValue() {
        return value;
    }

    /**
     * Nustato bito reikšmę. Reikšmė saugoma tik nurodytu moduliu.
     *
     * @param value nauja reikšmė
     */
    public void setValue(int value) {
        this.value = (value % modulus);
    }

    /**
     *
     * Nustatoma nauja bito reikšmė pagal duotą kitą bitą. Abiejų bitų moduliai privalo sutapti.
     *
     * @param bit nauja reikšmė
     * @throws IllegalArgumentException
     */
    public void setValue(Bit bit) throws IllegalArgumentException {

        if (this.getModulus() == bit.getModulus()) {
            this.value = (bit.getValue() % modulus);
        } else {
            throw new IllegalArgumentException("Moduliai nesutampa ("
                    + this.getModulus() + " ir " + bit.getModulus() + " )");
        }
    }

    /**
     * Prideda paduotą reikšmę prie esamos
     *
     * @param additionValue pridedama reikšmė
     */
    public void add(int additionValue) {
        value = (additionValue + value) % modulus;
    }

    /**
     * Prideda nurodyto bito reikšmę prie esamos.
     * Moduliai privalo sutapti
     *
     * @param bit pridedama reikšmė
     */
    public void add(Bit bit) {
        if (bit.getModulus() == this.getModulus()) {
            value = (bit.getValue() + value) % modulus;
        } else {
            throw new IllegalArgumentException("Moduliai nesutampa ("
                    + this.getModulus() + " ir " + bit.getModulus() + " )");
        }

    }

    /**
     * Nustato nulinę reikšmę
     */
    public void nullify() {
        value = 0;
    }

    /**
     * Grąžina modulį
     *
     * @return modulis
     */
    public int getModulus() {
        return modulus;
    }

    /**
     * Grąžina reikšmę tekstiniu formatu
     *
     * @return reikšmė tekstiniu formatu
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

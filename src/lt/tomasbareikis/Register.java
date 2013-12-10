package lt.tomasbareikis;

/**
 * Registras. Naudojamas dekoderyje ir enkoderyje.
 */
public class Register {

    // Registro reikšmė
    Bit value;

    /**
     * Sukuriamas naujas registras, galintis turėti reišmes, lygias 0 arba 1.
     * Pradinė reikšmė 0.
     */
    public Register() {
        this.value = new Bit(2, 0);
    }

    /**
     * Sukuriamas naujas reigistras su nurodyta reikšme.
     *
     * @param value registro reikšmė
     */
    public Register(Bit value) {
        this.value = value;
    }

    /**
     *
     * Grąžina registro reikšmę
     *
     * @return registro reikšmė
     */
    public Bit getValue() {
        return value;
    }

    /**
     * Grąžina registro reikmšmę kaip sveiką skaičių
     *
     * @return registro reikšmė
     */
    public int getValueAsInt() {
        return value.getValue();
    }

    /**
     * Grąžina registro reikšmę kaip simbolį
     *
     * @return registro reikšmė
     */
    public String getValueAsString() {
        return String.valueOf(this.getValueAsInt());
    }

    /**
     * Nustato registro reikšmę tokia pat, kaip ir nurodyta
     *
     * @param value nauja regsistro reikšmė
     * @throws IllegalArgumentException
     */
    public void setValue(Bit value) throws IllegalArgumentException{
        this.value = value;
    }

    /**
     * Nustato registro reikšmę tokia pat, kaip ir nurodytas sveikasis skaičius
     *
     * @param valueToSet nauja registro reikšmė
     */
    public void setValue(int valueToSet) {
        this.value.setValue(valueToSet);
    }

    /**
     * Prideda prie dabartinės registro reikšmės nurodytą reikšmę
     *
     * @param valueToAdd pridedama reikšmė
     * @throws IllegalArgumentException
     */
    public void addValue(Bit valueToAdd) throws IllegalArgumentException{
        this.value.add(valueToAdd);
    }

    /**
     * Prideda prie dabartinės registro reikšmės nurodytą sveikąjį skaičių
     *
     * @param valueToAdd pridedamas sveikasis skaičius
     */
    public void addValue(int valueToAdd) {
        this.value.add(valueToAdd);
    }

    /**
     * Nustato reigistro reikšmę lygią 0
     */
    public void nullifyValue() {
        this.value.nullify();
    }

    /**
     * Grąžina objekto tekstinę reikšmę
     * @return objekto rekstinė reikšmė
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

package lt.tomas.bareikis;

/**
 * Papildoma registrų grupė, naudojama tik dekoderyje. Sudaryta iš 6 registrų.
 */
public class DecoderRegisterGroup extends RegisterGroup {

    public DecoderRegisterGroup() {
        super();

        // Sukuriame registrus
        for (int i = 0; i < 6; i++) {
            this.registers.addLast(new Register());
        }
    }

    /**
     * Šios registrų grupės reigstrų reikšmėms "pastumti" naudojamas kitoks algoritmas,
     * nei kad įprastos registro grupės registrams. Naudojamas dekodavimas su grįžtamuoju ryšiu.
     *
     * @param inputBit iš kanalo atėjęs vienas iš bitų
     * @param resultBit iš MDE išėjęs rezultato bitas
     */
    public void shiftRegisters(Bit inputBit, Bit resultBit) {

        // Registrų numeriai prasideda nuo 0 (5 - paskutinis registras)
        // Paskutiniam registrui nustatoma prieš paskutinio registro reikšmė
        this.setRegisterValueAt(5, this.getRegisterValueAt(4));

        // Piriešpaskutinio bito reikšmę nustatome tokią, kaip ir 4-to bito (indeksas 3) reikšmę
        Bit bitForRegister4 = new Bit(this.getRegisterValueAt(3));
        // XOR'iname priešpaskutinio bito naują reikšmę su rezultato iš MDE bitu
        bitForRegister4.add(resultBit);
        this.setRegisterValueAt(4, bitForRegister4);

        // Atitinkamai pagal dekoderio schemą elgiamės su anksčiau esančiais registrais

        this.setRegisterValueAt(3, this.getRegisterValueAt(2));
        this.setRegisterValueAt(2, this.getRegisterValueAt(1));

        Bit bitForRegister1 = new Bit(this.getRegisterValueAt(0));
        bitForRegister1.add(resultBit);
        this.setRegisterValueAt(1, bitForRegister1);

        // Pirmojo bito reikšmė bus tokia pati kaip ir rezultato bito..
        Bit bitForRegister0 = new Bit(inputBit);
        // atlikus XOR operaciją su iš kanalo atėjusiu bitu
        bitForRegister0.add(resultBit);
        this.setRegisterValueAt(0, bitForRegister0);
    }
}

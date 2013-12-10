package lt.tomasbareikis;

import java.util.LinkedList;

/**
 * Tarpusavije sujungtų registrų seka (grupė).
 */
public class RegisterGroup {

    // Registrų sąrašas
    protected LinkedList<Register> registers;


    /**
     * Sukuria naują registrų grupę
     */
    public RegisterGroup() {
        registers = new LinkedList<Register>();
    }

    /**
     * Grąžina registro, esančio nurodytoje pozicijoje reikšmę
     *
     * @param index registro numeris
     * @return registro reikšmė
     */
    public Bit getRegisterValueAt(int index) {
        return registers.get(index).getValue();
    }

    /**
     * Grąžina registro, esančio sąrašo gale reikšmę
     *
     * @return registro reikšmė
     */
    public Bit getLastRegisterValue() {
        return registers.getLast().getValue();
    }

    /**
     * Nustato registro, nurodytoje pozicijoje, reikšmę
     *
     * @param index registro numeris
     * @param newValue nauja registro reikšmė
     */
    public void setRegisterValueAt(int index, Bit newValue) {
        registers.get(index).setValue(new Bit(newValue));
    }

    /**
     * Pastuma visų registrų reikšmes į "dešinę". Pirmam registrui nustatoma nauja reikšmė.
     *
     * @param inputBit naujas įėjęs į registrų grupę bitas
     */
    public void shiftRegisters(Bit inputBit) {

        if (registers.size() > 0) {
            int index = registers.size() - 1;

            // "Pastumame" registrų reikšmes. Kiekvienas registras gauna prieš jį esančio registro reikšmę
            for (int i = index; i > 0; i--) {
                registers.get(i).setValue(new Bit(registers.get(i-1).getValue()));
            }

            // Pirmasis registras gauna įėjusio bito reikšmę
            registers.get(0).setValue(inputBit);
        }
    }

    /**
     * Grąžina visų registrų reikšmes kaip bitų seką.
     *
     * @return visų registrų reikšmės
     */
    public DataStream getRegistersValuesAsDataStream() {
        DataStream result = new DataStream();

        for (Register register: this.registers) {
            result.appendToStreamEnd(new Bit(register.getValue()));
        }

        return result;

    }

}

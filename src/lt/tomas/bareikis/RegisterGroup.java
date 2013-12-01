package lt.tomas.bareikis;

import java.util.LinkedList;

public class RegisterGroup {

    protected LinkedList<Register> registers;


    public RegisterGroup() {
        registers = new LinkedList<Register>();
    }

    public Bit getRegisterValueAt(int index) {
        return registers.get(index).getValue();
    }

    public Bit getLastRegisterValue() {
        return registers.getLast().getValue();
    }

    public void setRegisterValueAt(int index, Bit newValue) {
        registers.get(index).setValue(new Bit(newValue));
    }

    /**
     *
     * @param inputBit įėjęs į enkoderį bitas
     */
    public void shiftRegisters(Bit inputBit) {

        if (registers.size() > 0) {
            int index = registers.size()-1;

            // "Pastumame" registrų reikšmes. Kiekvienas registras gauna prieš jį esančio registro reikšmę
            for (int i = index; i > 0; i--) {
                registers.get(i).setValue(new Bit(registers.get(i-1).getValue()));
            }

            // Pirmasis registras gauna įėjusio bito reikšmę
            registers.get(0).setValue(inputBit);
        }
    }

    public DataStream getRegistersValuesAsDataStream() {
        DataStream result = new DataStream();

        for (Register register: this.registers) {
            result.appendToStreamEnd(new Bit(register.getValue()));
        }

        return result;

    }

}

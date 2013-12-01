package lt.tomas.bareikis;


public class DecoderRegisterGroup extends RegisterGroup {

    public DecoderRegisterGroup() {
        super();

        for (int i = 0; i < 6; i++) {
            this.registers.addLast(new Register());
        }
    }

    public void shiftRegisters(Bit inputBit, Bit resultBit) {

        this.setRegisterValueAt(5, this.getRegisterValueAt(4));

        Bit bitForRegister4 = new Bit(this.getRegisterValueAt(3));
        bitForRegister4.add(resultBit);
        this.setRegisterValueAt(4, bitForRegister4);

        this.setRegisterValueAt(3, this.getRegisterValueAt(2));
        this.setRegisterValueAt(2, this.getRegisterValueAt(1));

        Bit bitForRegister1 = new Bit(this.getRegisterValueAt(0));
        bitForRegister1.add(resultBit);
        this.setRegisterValueAt(1, bitForRegister1);

        Bit bitForRegister0 = new Bit(inputBit);
        bitForRegister0.add(resultBit);
        this.setRegisterValueAt(0, bitForRegister0);

    }
}

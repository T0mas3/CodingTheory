package lt.tomas.bareikis;

public class EncoderRegisterGroup extends RegisterGroup {

    public EncoderRegisterGroup() {
        super();

        for (int i = 0; i < 6; i++) {
            this.registers.addLast(new Register());
        }
    }
}

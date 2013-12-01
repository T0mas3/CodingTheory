package lt.tomas.bareikis;

public class CommonRegisterGroup extends RegisterGroup {

    public CommonRegisterGroup() {
        super();

        for (int i = 0; i < 6; i++) {
            this.registers.addLast(new Register());
        }
    }
}

package lt.tomas.bareikis;

/**
 * "Bendra" registrų grupė. Pavadinta "Bendra", nes naudojama ir enkoderyje ir dekoderyje. Sudaryta iš 6 registrų.
 */
public class CommonRegisterGroup extends RegisterGroup {

    public CommonRegisterGroup() {
        super();

        // sukuriame 6 resigtrus
        for (int i = 0; i < 6; i++) {
            this.registers.addLast(new Register());
        }
    }
}

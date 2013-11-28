package lt.tomas.bareikis.tests

import lt.tomas.bareikis.Bit
import lt.tomas.bareikis.Register
import org.junit.Test

class RegisterTest {

    @Test
    void initialisationIsWorking1() {
        Register register = new Register();

        assert register.getValue().getValue() == 0;
    }

    @Test
    void initialisationIsWorking2() {
        Register register = new Register(new Bit(1));

        assert register.getValue().getValue() == 1;
    }

    @Test
    void initialisationIsWorking3() {
        Register register = new Register(new Bit(6));

        assert register.getValue().getValue() == 0;
    }

    @Test
    void additionIsWorking1() {
        Register register = new Register();
        register.addValue(new Bit(1));

        assert register.getValue().getValue() == 1;
    }

    @Test
    void additionIsWorking2() {
        Register register = new Register();
        register.setValue(new Bit(1))
        register.addValue(new Bit(1));

        assert register.getValue().getValue() == 0;
    }

    @Test
    void additionIsWorking3() {
        Register register = new Register();
        register.setValue(new Bit(1))
        register.addValue(1);

        assert register.getValue().getValue() == 0;
    }

    @Test
    void setValueIsWorking1() {
        Register register = new Register();
        register.setValue(1);

        assert register.getValue().getValue() == 1;
    }

    @Test
    void setValueIsWorking2() {
        Register register = new Register();
        register.setValue(0);

        assert register.getValue().getValue() == 0;
    }

}

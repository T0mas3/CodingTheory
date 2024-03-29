package lt.tomasbareikis.tests

import lt.tomasbareikis.Bit
import org.junit.Test

class BitTest {

    @Test
    void initialisationIsWorking1() {
        Bit bit = new Bit(1);

        assert bit.getValue() == 1;
    }

    @Test
    void initialisationIsWorking2() {
        Bit bit = new Bit(2, 5);

        assert bit.getValue() == 1;
    }

    @Test
    void initialisationIsWorking3() {
        Bit bit = new Bit(3, 5);

        assert bit.getValue() == 2;
    }

    @Test
    void initialisationIsWorkingWithChar1() {
        String str = "0";
        Bit bit = new Bit(str.charAt(0));

        assert bit.getValue() == 0;
    }

    @Test
    void initialisationIsWorkingWithChar2() {
        String str = "1";
        Bit bit = new Bit(str.charAt(0));

        assert bit.getValue() == 1;
    }

    @Test
    void initialisationIsWorkingWithChar3() {
        String str = "5";
        Bit bit = new Bit(str.charAt(0));

        assert bit.getValue() == 1;
    }

    @Test
    void additionIsWorking1() {
        Bit bit1 = new Bit();
        bit1.add(1);

        assert bit1.getValue() == 1;
    }

    @Test
    void additionIsWorking2() {
        Bit bit1 = new Bit();
        bit1.add(2);

        assert bit1.getValue() == 0;
    }

    @Test
    void additionIsWorking3() {
        Bit bit1 = new Bit();
        bit1.add(3);

        assert bit1.getValue() == 1;
    }

    @Test
    void additionWithBitIsWorking1() {
        Bit bit1 = new Bit();
        Bit bit2 = new Bit();
        bit1.add(bit2);

        assert bit1.getValue() == 0;
    }

    @Test
    void additionWithBitIsWorking2() {
        Bit bit1 = new Bit();
        Bit bit2 = new Bit();
        bit2.setValue(1);
        bit1.add(bit2);

        assert bit1.getValue() == 1;
    }

    @Test
    void additionWithBitIsWorking3() {
        Bit bit1 = new Bit();
        bit1.setValue(1)
        Bit bit2 = new Bit();
        bit2.setValue(1);
        bit1.add(bit2);

        assert bit1.getValue() == 0;
    }

    @Test
    void setBitIsWorking1() {
        Bit bit1 = new Bit();
        bit1.setValue(1)
        Bit bit2 = new Bit();
        bit2.setValue(bit1);

        assert bit2.getValue() == 1;
    }

}

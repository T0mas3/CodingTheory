package lt.tomasbareikis.tests

import lt.tomasbareikis.DataStream
import lt.tomasbareikis.MDE
import org.junit.Test

class MDETest {

    @Test
    void mdeIsWorking1() {
        MDE mde = new MDE();

        assert mde.calculate(new DataStream("1111")).getValue() == 1;
    }

    @Test
    void mdeIsWorking2() {
        MDE mde = new MDE();

        assert mde.calculate(new DataStream("1110")).getValue() == 1;
    }

    @Test
    void mdeIsWorking3() {
        MDE mde = new MDE();

        assert mde.calculate(new DataStream("1011")).getValue() == 1;
    }

    @Test
    void mdeIsWorking4() {
        MDE mde = new MDE();

        assert mde.calculate(new DataStream("0010")).getValue() == 0;
    }

    @Test
    void mdeIsWorking5() {
        MDE mde = new MDE();

        assert mde.calculate(new DataStream("0")).getValue() == 0;
    }

    @Test
    void mdeIsWorking6() {
        MDE mde = new MDE();

        assert mde.calculate(new DataStream("1")).getValue() == 1;
    }
}

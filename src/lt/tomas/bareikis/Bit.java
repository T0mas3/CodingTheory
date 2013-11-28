package lt.tomas.bareikis;

public class Bit {

    int modulus = 2;
    int value = 0;

    public Bit(int modulus, int value) {
        this.modulus = modulus;
        this.value = value % modulus;
    }

    public Bit(int value) {
        this.value = value % modulus;
    }

    public Bit() {}

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = (value % modulus);
    }

    public void setValue(Bit bit) {
        this.value = (bit.getValue() % modulus);
    }

    public void add(int additionValue) {
        value = (additionValue + value) % modulus;
    }

    public void add(Bit bit) {
        value = (bit.getValue() + value) % modulus;
    }

    public void nullify() {
        value = 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

package lt.tomas.bareikis;

public class Register {

    Bit value;

    public Register() {
        this.value = new Bit(2, 0);
    }

    public Register(Bit value) {
        this.value = value;
    }

    public Bit getValue() {
        return value;
    }

    public int getValueAsInt() {
        return value.getValue();
    }

    public String getValueAsString() {
        return String.valueOf(this.getValueAsInt());
    }

    public void setValue(Bit value) throws IllegalArgumentException{
        this.value = value;
    }

    public void setValue(int valueToSet) {
        this.value.setValue(valueToSet);
    }

    public void addValue(Bit valueToAdd) throws IllegalArgumentException{
        this.value.add(valueToAdd);
    }

    public void addValue(int valueToAdd) {
        this.value.add(valueToAdd);
    }

    public void nullifyValue() {
        this.value.nullify();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

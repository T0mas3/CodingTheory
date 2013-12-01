package lt.tomas.bareikis;

import java.util.LinkedList;

public class DataStream {

    LinkedList<Bit> data;

    public DataStream() {
        this.data = new LinkedList<Bit>();
    }

    public DataStream(String string) {
        this();

        for (int i = 0; i < string.length(); i++){
            char character = string.charAt(i);

            Bit bit = new Bit(character);
            this.appendToStreamEnd(bit);
        }
    }

    public void appendToStreamEnd(Bit bit) {
        this.data.addLast(bit);
    }

    public Bit getDataAt(int index) throws IndexOutOfBoundsException{
        return data.get(index);
    }

    public DataStream getSubStream(int from, int to) {

        DataStream subList = new DataStream();
        for (int i = from; i < to; i++) {
            subList.appendToStreamEnd(new Bit(this.data.get(i)));
        }
        return subList;
    }

    public int getSize() {
        return data.size();
    }

    public LinkedList<Bit> getDataAsBitList() {
        return this.data;
    }

    public void appendToStreamEnd(DataStream encodedPart) {
        this.data.addAll(encodedPart.getDataAsBitList());
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof  DataStream) {
            DataStream compareTo = (DataStream) obj;

            if (compareTo.getSize() != this.getSize()) {
                return false;
            }

            for (int i = 0; i < this.data.size(); i++) {
                if (this.getDataAt(i).getValue() != compareTo.getDataAt(i).getValue()) {
                    return false;
                }

                if (this.getDataAt(i).getModulus() != compareTo.getDataAt(i).getModulus()) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }
}

package lt.tomas.bareikis;

import java.util.LinkedList;

/**
 * Duomenų srautas. Tai yra tiesiog bitų seka.
 */
public class DataStream {

    LinkedList<Bit> data;

    public DataStream() {
        this.data = new LinkedList<Bit>();
    }

    /**
     *
     * Sukuria naują bitų seką iš paduotos eilutės.
     * Iš kiekvieno tekstinės eilutės simbolio yra sudaromas naujas bitas.
     * Šiuo atveju iš teksto eilutės, sudarytos iš 0 ir 1 yra sudaroma bitų seka.
     *
     * @param string bitų seka tekstinės eilutės pavidalu
     */
    public DataStream(String string) {
        this();

        for (int i = 0; i < string.length(); i++){
            char character = string.charAt(i);

            Bit bit = new Bit(character);
            this.appendToStreamEnd(bit);
        }
    }

    /**
     * Prideda naują bitą į sekos pabaigą
     * @param bit naujas bitas
     */
    public void appendToStreamEnd(Bit bit) {
        this.data.addLast(bit);
    }

    /**
     * Grąžina bitą, esantį nurodytoje pozicijoje
     *
     * @param index pozicija
     * @return bitas nurodytoje pozicijoje
     * @throws IndexOutOfBoundsException
     */
    public Bit getDataAt(int index) throws IndexOutOfBoundsException{
        return data.get(index);
    }

    /**
     * Grąžina naują bitų seką, kuri yra dalis esamos bitų sekos nuo pradžios indekso iki pabaigos.
     *
     * @param from nuo
     * @param to iki
     * @return nauja bitų seka
     */
    public DataStream getSubStream(int from, int to) {

        DataStream subList = new DataStream();
        for (int i = from; i < to; i++) {
            subList.appendToStreamEnd(new Bit(this.data.get(i)));
        }
        return subList;
    }

    /**
     * Grąžina bitų sekos ilgį.
     *
     * @return bitų sekos ilgis
     */
    public int getSize() {
        return data.size();
    }

    /**
     * Grąžina bitus iš sekos kaip sąrašą.
     *
     * @return bitų sąrašas
     */
    public LinkedList<Bit> getDataAsBitList() {
        return this.data;
    }

    /**
     *
     * Pridjungia paduotą bitų seką prie esamos sekos pabaigos
     *
     * @param encodedPart prijungiama bitų seka
     */
    public void appendToStreamEnd(DataStream encodedPart) {
        this.data.addAll(encodedPart.getDataAsBitList());
    }

    /**
     * Grąžina suformuotą tekstinę eilutę iš esamos bitų sekos.
     *
     * @return tekstinė eilutė
     */
    public String toStringOfBytes() {
        String result = "";
        for (Bit bit: this.data) {
            result += bit.getValue();
        }

        return result;
    }

    /**
     * Grąžina šio objekto atvaizdavimą tekstine eilute.
     *
     * @return tekstinė eilutė
     */
    @Override
    public String toString() {
        return data.toString();
    }

    /**
     * Patikrina, ar paduota bitų seka yra identiška šiai sekai.
     * Visų bitų moduliai sekose privalo sutapti
     *
     * @param obj bitų seka
     * @return ar identiška
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof  DataStream) {
            DataStream compareTo = (DataStream) obj;

            if (compareTo.getSize() != this.getSize()) {
                return false;
            }

            for (int i = 0; i < this.data.size(); i++) {
                // Ar reikšmė sutampa?
                if (this.getDataAt(i).getValue() != compareTo.getDataAt(i).getValue()) {
                    return false;
                }

                // Ar modulis sutampa?
                if (this.getDataAt(i).getModulus() != compareTo.getDataAt(i).getModulus()) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public LinkedList<Integer> getMismatchPositions(DataStream dataStreamToCompare) {

        LinkedList<Integer> mismatches = new LinkedList<Integer>();

        for (int i = 0; i < this.data.size(); i++) {
            // Ar reikšmė sutampa?
            if (this.getDataAt(i).getValue() != dataStreamToCompare.getDataAt(i).getValue()) {
                mismatches.add(i);
            }
        }

        return mismatches;
    }
}

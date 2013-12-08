package lt.tomas.bareikis;

import java.util.HashMap;
import java.util.Map;

/**
 * Majority Decision Element
 * Nustato, kurios iš paduotų reikšmių sekoje yra daugiausia.
 */
public class MDE {

    public MDE() {}

    /**
     * Nustato, kurios iš paduotų reikšmių sekoje yra daugiausia.
     * Jei pasikartoja kelios reikšmės tiek pat kartų, tai imama pirma sąraše.
     *
     * @param dataStream duomenų seka
     * @return daugiausiai pasikartojimų turinti reikšmė
     */
    public Bit calculate(DataStream dataStream) {

        Bit result = new Bit(); // daugiausiai kartų pasikartojusi reikšmė

        // Sukuriame rakto - reikšmės porų sąrašą. Raktas - bito reikšmė, veikšmė - pasikartojimų skaičius
        Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

        for (Bit bit: dataStream.getDataAsBitList()) {
            this.storeValue(frequencyMap, bit);
        }

        int maxOccurrences = 0;

        // Randame daugiausiai kartų pasikartojančią reikšmę
        for (Map.Entry<Integer, Integer> valuePairs : frequencyMap.entrySet()) {

            Integer numberOfOccurrences = valuePairs.getValue(); // Pasikartojimų skaičius
            Integer bitValue = valuePairs.getKey(); // Bito reikšmė

            if (numberOfOccurrences > maxOccurrences) {
                // Radome daugiau kartų pasikartojusią reikšmę
                result = new Bit(bitValue);
                maxOccurrences = numberOfOccurrences;
            }
        }

        return result;
    }

    /**
     * Prideda naują reikšmę rakto - reikšmės porų sąraše
     *
     * @param frequencyMap rakto - reikšmės porų sąrašas
     * @param bit nauja reikšmė
     */
    private void storeValue(Map<Integer, Integer> frequencyMap, Bit bit) {
        if (frequencyMap.get(bit.getValue()) != null) {
            // Jei jau tokia reikšmė yra, tai jos pasikartojimų skaičių padidiname vienetu
            frequencyMap.put(bit.getValue(), frequencyMap.get(bit.getValue())+1);
        } else {
            // Jei ne, tai pridedame tą reikšmė, su pasikartojimų skaičiumi, lygiu 1
            frequencyMap.put(bit.getValue(), 1);
        }
    }
}

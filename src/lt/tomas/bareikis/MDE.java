package lt.tomas.bareikis;

import java.util.HashMap;
import java.util.Map;

public class MDE {

    public MDE() {}

    public Bit calculate(DataStream dataStream) {

        Bit result = new Bit();

        Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

        for (Bit bit: dataStream.getDataAsBitList()) {
            this.storeValue(frequencyMap, bit);
        }

        int maxOccurrences = 0;

        for (Map.Entry<Integer, Integer> valuePairs : frequencyMap.entrySet()) {
            Integer numberOfOccurrences = valuePairs.getValue();
            Integer bitValue = valuePairs.getKey();
            if (numberOfOccurrences > maxOccurrences) {
                result = new Bit(bitValue);
                maxOccurrences = numberOfOccurrences;
            }
        }

        return result;
    }

    private void storeValue(Map<Integer, Integer> frequencyMap, Bit bit) {
        if (frequencyMap.get(bit.getValue()) != null) {
            frequencyMap.put(bit.getValue(), frequencyMap.get(bit.getValue())+1);
        } else {
            frequencyMap.put(bit.getValue(), 1);
        }
    }

}

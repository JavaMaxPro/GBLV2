package task1;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    public static final String[] MAS = new String[]{"a", "a", "b", "b", "b", "c", "d", "d", "e", "f", "g"};

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>(12, 1);
        for (String ma : MAS) {
            Integer slov = map.get(ma);
            if (slov == null) {
                slov = 0;
            }
            map.put(ma, ++slov);
        }

        vivod(map);
    }

    private static void vivod(Map<String, Integer> map) {
        for (String s : map.keySet()) {
            System.out.println(s + " - " + (Integer) map.get(s));
        }
    }
}

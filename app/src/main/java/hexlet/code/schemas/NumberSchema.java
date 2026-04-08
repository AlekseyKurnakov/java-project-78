package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public class NumberSchema {
    private boolean required;
    private Integer positive;
    private Map<String, Integer> range;

    public NumberSchema() {
        required = false;
        positive = null;
        range = new HashMap<>();
    }

    public void required() {
        required = true;
    }
    public void positive() {
        positive = 0;
    }
    public void range(int min, int max) {
        if (min > max) {
            int maxNum = min;
            min = max;
            max = maxNum;
        }
        range.put("min", min);
        range.put("max", max);
    }

    public boolean isValid(Integer num) {
        if (required) {
            if(num == null) {
                return false;
            }
        }
        if (positive != null && num < positive) {
            return false;
        }
        if (!range.isEmpty()) {
            int min = range.get("min");
            int max = range.get("max");
            if (num < min || num > max) {
                return false;
            }
        }
        return true;
    }
}

package hexlet.code.schemas;


public class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        addValidator("required", value -> value != null);
        return this;
    }

    public NumberSchema positive() {
        addValidator("positive", value -> value == null || value > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        int left = Math.min(min, max);
        int right = Math.max(min, max);

        addValidator("range", value -> value == null || (value >= left && value <= right));
        return this;
    }
}

package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema<T> {
    private final Map<String, Predicate<T>> validators = new HashMap<>();

    protected final void addValidator(String name, Predicate<T> validator) {
        validators.put(name, validator);
    }

    public final boolean isValid(T value) {
        for (var validator : validators.values()) {
            if (!validator.test(value)) {
                return false;
            }
        }
        return true;
    }
}

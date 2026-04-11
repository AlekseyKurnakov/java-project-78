package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema required() {
        addValidator("required", value -> value != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        addValidator("sizeof", value -> value == null || value.size() == size);
        return this;
    }
    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        addValidator("shape", value -> {
            if (value == null) {
                return true;
            }

            for (var entry : schemas.entrySet()) {
                var key = entry.getKey();
                var schema = entry.getValue();
                var fieldValue = value.get(key);

                @SuppressWarnings("unchecked")
                var typedSchema = (BaseSchema<Object>) schema;

                if (!typedSchema.isValid(fieldValue)) {
                    return false;
                }
            }

            return true;
        });

        return this;
    }

}

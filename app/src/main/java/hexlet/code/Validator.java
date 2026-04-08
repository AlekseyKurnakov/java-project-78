package hexlet.code;

import hexlet.code.schemas.StringSchema;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Validator {

    public StringSchema string() {
        return new StringSchema();
    }
}

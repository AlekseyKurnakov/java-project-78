package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class StringSchemaTest {

    private StringSchema schema;
    private final String TEXT = "what does the fox say";

    @BeforeEach
    void creationStringSchema() {
        Validator v = new Validator();
        schema = v.string();
    }

    @Test
    void WithoutMethodsTest() {
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(TEXT));
    }
    @Test
    void WithMethodRequiredTest() {
        schema.required();
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(TEXT));
    }
    @Test
    void WithMethodMinLengthTest() {
        schema.minLength(25);
        assertFalse(schema.isValid(TEXT));
        schema.minLength(5);
        assertTrue(schema.isValid(TEXT));
    }
    @Test
    void WithMethodContainsTest() {
        schema.contains("what");
        assertTrue(schema.isValid(TEXT));
        schema.contains("whatthe");
        assertFalse(schema.isValid(TEXT));
    }

}

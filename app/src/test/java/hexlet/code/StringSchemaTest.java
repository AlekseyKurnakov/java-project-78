package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringSchemaTest {

    private StringSchema schema;
    private final String text = "what does the fox say";

    @BeforeEach
    void setUp() {
        Validator validator = new Validator();
        schema = validator.string();
    }

    @Test
    void isValidWithoutRulesTest() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(text));
    }

    @Test
    void requiredTest() {
        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid(text));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    void minLengthTest() {
        schema.minLength(5);

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("hello"));
        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid("cat"));
    }

    @Test
    void containsTest() {
        schema.contains("wh");

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(text));
        assertFalse(schema.isValid("hexlet"));
    }

    @Test
    void multipleRulesTest() {
        schema.required().minLength(5).contains("fox");

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("cat"));
        assertFalse(schema.isValid("what"));
        assertTrue(schema.isValid(text));
    }

    @Test
    void lastMinLengthHasPriorityTest() {
        schema.minLength(10).minLength(4);

        assertTrue(schema.isValid("Hexlet"));
    }

    @Test
    void lastContainsHasPriorityTest() {
        schema.contains("wh").contains("fox");

        assertTrue(schema.isValid(text));
        assertFalse(schema.isValid("what does the cat say"));
    }
}

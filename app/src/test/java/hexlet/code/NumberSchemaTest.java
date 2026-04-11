package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class NumberSchemaTest {

    private NumberSchema schema;

    @BeforeEach
    void setUp() {
        Validator validator = new Validator();
        schema = validator.number();
    }

    @Test
    void isValidWithoutRulesTest() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(-10));
    }

    @Test
    void requiredTest() {
        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(10));
        assertTrue(schema.isValid(0));
    }

    @Test
    void positiveTest() {
        schema.positive();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(-10));
    }

    @Test
    void rangeTest() {
        schema.range(5, 10);

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(7));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(11));
    }

    @Test
    void multipleRulesTest() {
        schema.required().positive().range(5, 10);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(-5));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(4));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(11));
    }

    @Test
    void lastRangeHasPriorityTest() {
        schema.range(1, 5).range(10, 20);

        assertFalse(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertTrue(schema.isValid(20));
        assertFalse(schema.isValid(21));
    }
}

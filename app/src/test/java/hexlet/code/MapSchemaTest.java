package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MapSchemaTest {

    private Validator validator;
    private MapSchema schema;
    private Map<String, String> data;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        schema = validator.map();
        data = new HashMap<>();
    }

    @Test
    void isValidWithoutRulesTest() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(data));

        data.put("key1", "value1");
        assertTrue(schema.isValid(data));
    }

    @Test
    void requiredTest() {
        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(data));

        data.put("key1", "value1");
        assertTrue(schema.isValid(data));
    }

    @Test
    void sizeofTest() {
        schema.sizeof(2);

        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(data));

        data.put("key1", "value1");
        assertFalse(schema.isValid(data));

        data.put("key2", "value2");
        assertTrue(schema.isValid(data));

        data.put("key3", "value3");
        assertFalse(schema.isValid(data));
    }

    @Test
    void lastSizeofHasPriorityTest() {
        schema.sizeof(2).sizeof(3);

        data.put("key1", "value1");
        data.put("key2", "value2");
        assertFalse(schema.isValid(data));

        data.put("key3", "value3");
        assertTrue(schema.isValid(data));
    }

    @Test
    void shapeWithoutRequiredTest() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        schema.shape(schemas);

        assertTrue(schema.isValid(null));
    }

    @Test
    void shapeValidMapTest() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        schema.shape(schemas);

        Map<String, String> human = new HashMap<>();
        human.put("firstName", "John");
        human.put("lastName", "Smith");

        assertTrue(schema.isValid(human));
    }

    @Test
    void shapeInvalidWhenFieldIsNullTest() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        schema.shape(schemas);

        Map<String, String> human = new HashMap<>();
        human.put("firstName", "John");
        human.put("lastName", null);

        assertFalse(schema.isValid(human));
    }

    @Test
    void shapeInvalidWhenMinLengthFailsTest() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        schema.shape(schemas);

        Map<String, String> human = new HashMap<>();
        human.put("firstName", "Anna");
        human.put("lastName", "B");

        assertFalse(schema.isValid(human));
    }

    @Test
    void shapeValidWhenOptionalFieldIsMissingTest() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("nickname", validator.string().minLength(3));

        schema.shape(schemas);

        Map<String, String> human = new HashMap<>();
        human.put("firstName", "John");

        assertTrue(schema.isValid(human));
    }

    @Test
    void shapeInvalidWhenRequiredFieldIsMissingTest() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required());

        schema.shape(schemas);

        Map<String, String> human = new HashMap<>();
        human.put("firstName", "John");

        assertFalse(schema.isValid(human));
    }

    @Test
    void requiredAndShapeTogetherTest() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());

        schema.required().shape(schemas);

        assertFalse(schema.isValid(null));

        Map<String, String> human = new HashMap<>();
        human.put("firstName", "John");

        assertTrue(schema.isValid(human));
    }

    @Test
    void lastShapeHasPriorityTest() {
        Map<String, BaseSchema<?>> firstSchemas = new HashMap<>();
        firstSchemas.put("name", validator.string().required().minLength(5));

        Map<String, BaseSchema<?>> secondSchemas = new HashMap<>();
        secondSchemas.put("name", validator.string().required().minLength(2));

        schema.shape(firstSchemas).shape(secondSchemas);

        Map<String, String> human = new HashMap<>();
        human.put("name", "Joe");

        assertTrue(schema.isValid(human));
    }
}

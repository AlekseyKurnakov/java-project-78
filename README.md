# Валидатор данных

[![Actions Status](https://github.com/AlekseyKurnakov/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/AlekseyKurnakov/java-project-78/actions)
[![Checkstyle](https://github.com/AlekseyKurnakov/java-project-78/actions/workflows/checkstyle.yml/badge.svg)](https://github.com/AlekseyKurnakov/java-project-78/actions/workflows/checkstyle.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AlekseyKurnakov_java-project-78&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=AlekseyKurnakov_java-project-78)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=AlekseyKurnakov_java-project-78&metric=coverage)](https://sonarcloud.io/summary/new_code?id=AlekseyKurnakov_java-project-78)

## Описание

**Валидатор данных** — это небольшая библиотека для проверки разных типов данных с помощью схем.

Проект поддерживает валидацию:

- строк
- чисел
- map-объектов
- вложенных структур через `shape()`

Правила валидации настраиваются методами схемы, после чего проверка выполняется через `isValid()`.

---

## Возможности

### StringSchema
Поддерживает валидацию строк со следующими правилами:

- `required()` — значение не должно быть `null` или пустой строкой
- `minLength(int)` — минимальная длина строки
- `contains(String)` — строка должна содержать подстроку

### NumberSchema
Поддерживает валидацию чисел со следующими правилами:

- `required()` — значение не должно быть `null`
- `positive()` — число должно быть больше нуля
- `range(int, int)` — число должно попадать в указанный диапазон, включая границы

### MapSchema
Поддерживает валидацию map со следующими правилами:

- `required()` — значение не должно быть `null`
- `sizeof(int)` — размер map должен совпадать с указанным
- `shape(Map<String, BaseSchema<?>>)` — вложенная валидация значений по ключам

---

## Пример использования

```java
import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;

import java.util.HashMap;
import java.util.Map;

Validator validator = new Validator();

// Валидация строки
StringSchema stringSchema = validator.string();
stringSchema.required().minLength(5);

System.out.println(stringSchema.isValid("Hexlet")); // true
System.out.println(stringSchema.isValid("cat"));    // false

// Валидация числа
NumberSchema numberSchema = validator.number();
numberSchema.required().positive().range(1, 10);

System.out.println(numberSchema.isValid(5));   // true
System.out.println(numberSchema.isValid(0));   // false
System.out.println(numberSchema.isValid(15));  // false

// Валидация map
MapSchema mapSchema = validator.map();
mapSchema.required().sizeof(2);

Map<String, String> data = new HashMap<>();
data.put("firstName", "John");
data.put("lastName", "Smith");

System.out.println(mapSchema.isValid(data)); // true

// Вложенная валидация
Map<String, BaseSchema<?>> schemas = new HashMap<>();
schemas.put("firstName", validator.string().required());
schemas.put("lastName", validator.string().required().minLength(2));

MapSchema userSchema = validator.map();
userSchema.shape(schemas);

System.out.println(userSchema.isValid(data)); // true
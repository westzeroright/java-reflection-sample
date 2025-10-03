package ch9;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class PersonTest {
    @Test
    public void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() {
        Object person = new Person();
        Field[] fields = person.getClass().getDeclaredFields();

        List<String> actualFieldNames = getFieldNames(fields);

        assertTrue(Arrays.asList("name", "age")
                .containsAll(actualFieldNames));
    }

    private static List<String> getFieldNames(Field[] fields) {
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields)
            fieldNames.add(field.getName());
        return fieldNames;
    }
  
}

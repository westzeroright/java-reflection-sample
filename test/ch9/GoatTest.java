package ch9;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class GoatTest {
    @Test
    public void givenObject_whenGetsClassName_thenCorrect() {
        Object goat = new Goat("Goat");
        Class<?> clazz = goat.getClass();

        assertEquals("Goat", clazz.getSimpleName());
        assertEquals("ch9.Goat", clazz.getName());
        assertEquals("ch9.Goat", clazz.getCanonicalName());
    }

    @Test
    public void givenClassName_whenCreatesObject_thenCorrect() throws ClassNotFoundException{
        Class<?> clazz = Class.forName("ch9.Goat");

        assertEquals("Goat", clazz.getSimpleName());
        assertEquals("ch9.Goat", clazz.getName());
        assertEquals("ch9.Goat", clazz.getCanonicalName());
    }

    @Test
    public void givenClass_whenRecognisesModifiers_thenCorrect() throws ClassNotFoundException{
        Class<?> goatClass = Class.forName("ch9.Goat");
        Class<?> animalClass = Class.forName("ch9.Animal");

        int goatMods = goatClass.getModifiers();
        int animalMods = animalClass.getModifiers();

        assertTrue(Modifier.isPublic(goatMods));
        assertTrue(Modifier.isAbstract(animalMods));
    }

    @Test
    public void givenClass_whenGetsSuperClass_thenCorrect() {
        Goat goat = new Goat("goat");
        String str = "any string";

        Class<?> goatClass = goat.getClass();
        Class<?> goatSuperClass = goatClass.getSuperclass();

        assertEquals("Animal", goatSuperClass.getSimpleName());
        assertEquals("Object", str.getClass().getSuperclass().getSimpleName());
    }

    @Test
    public void givenClass_whenGetsImplementedInterfaces_thenCorrect() throws ClassNotFoundException{
        Class<?> goatClass = Class.forName("ch9.Goat");
        Class<?> animalClass = Class.forName("ch9.Animal");

        Class<?>[] goatInterfaces = goatClass.getInterfaces();
        Class<?>[] animalInterfaces = animalClass.getInterfaces();

        assertEquals(1, goatInterfaces.length);
        assertEquals(1, animalInterfaces.length);
        assertEquals("Locomotion", goatInterfaces[0].getSimpleName());
        assertEquals("Eating", animalInterfaces[0].getSimpleName());
    }

    @Test
    public void givenClass_whenGetsConstructor_thenCorrect() throws ClassNotFoundException{
        Class<?> goatClass = Class.forName("ch9.Goat");

        Constructor<?>[] constructors = goatClass.getConstructors();

        assertEquals(1, constructors.length);
        assertEquals("ch9.Goat", constructors[0].getName());
    }

    @Test
    public void givenClass_whenGetsFields_thenCorrect() throws ClassNotFoundException{
        Class<?> animalClass = Class.forName("ch9.Animal");
        Field[] fields = animalClass.getDeclaredFields();

        List<String> actualFields = getFieldNames(fields);

        assertEquals(2, actualFields.size());
        assertTrue(actualFields.containsAll(Arrays.asList("name", "CATEGORY")));
    }

    @Test
    public void givenClass_whenGetsMethods_thenCorrect() throws ClassNotFoundException{
        Class<?> animalClass = Class.forName("ch9.Animal");
        Method[] methods = animalClass.getDeclaredMethods();
        List<String> actualMethods = getMethodNames(methods);

        assertEquals(3, actualMethods.size());
        assertTrue(actualMethods.containsAll(Arrays.asList("getName",
                "setName", "getSound")));
    }

    private static List<String> getFieldNames(Field[] fields) {
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields)
            fieldNames.add(field.getName());
        return fieldNames;
    }

    // Method 객체 배열에서 메서드 이름을 검색하는 헬퍼 메서드
    private static List<String> getMethodNames(Method[] methods) {
        List<String> methodNames = new ArrayList<>();
        for (Method method : methods)
            methodNames.add(method.getName());
        return methodNames;
    }
}

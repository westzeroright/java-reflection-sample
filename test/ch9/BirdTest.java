package ch9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class BirdTest {
    @Test
    public void givenClass_whenGetsAllConstructors_thenCorrect() throws ClassNotFoundException {
        Class<?> birdClass = Class.forName("ch9.Bird");
        Constructor<?>[] constructors = birdClass.getConstructors();

        assertEquals(3, constructors.length);
    }

    /*
    선언된 순서대로 생성자의 매개변수 클래스 유형을 전달하여 Bird 클래스의 각 생성자를 검색
    - NoSuchMethodException이 발생하고 주어진 순서대로 주어진 매개변수 유형을 갖는 생성자가 존재하지 않으면 테스트가 자동으로 실패하므로 assertion이 필요하지 않다.
     */
    @Test
    public void givenClass_whenGetsEachConstructorByParamTypes_thenCorrect() throws ClassNotFoundException, NoSuchMethodException {
        Class<?> birdClass = Class.forName("ch9.Bird");

        Constructor<?> cons1 = birdClass.getConstructor();
        Constructor<?> cons2 = birdClass.getConstructor(String.class);
        Constructor<?> cons3 = birdClass.getConstructor(String.class, boolean.class);
    }

    @Test
    public void givenClass_whenInstantiatesObjectsAtRuntime_thenCorrect() throws Exception{
        Class<?> birdClass = Class.forName("ch9.Bird");
        Constructor<?> cons1 = birdClass.getConstructor();
        Constructor<?> cons2 = birdClass.getConstructor(String.class);
        Constructor<?> cons3 = birdClass.getConstructor(String.class,
                boolean.class);

        // 생성자 클래스의 newInstance 메서드를 호출하고 필요한 매개변수를 선언된 순서대로 전달하여 클래스 객체를 인스턴스화한다.
        Bird bird1 = (Bird) cons1.newInstance();
        Bird bird2 = (Bird) cons2.newInstance("Weaver bird");
        Bird bird3 = (Bird) cons3.newInstance("dove", true);

        assertEquals("bird", bird1.getName());
        assertEquals("Weaver bird", bird2.getName());
        assertEquals("dove", bird3.getName());

        assertFalse(bird1.walks());
        assertTrue(bird3.walks());
    }

    /*
    getFields()
    - 해당 클래스의 접근 가능한 모든 public 필드를 반환
    - 클래스와 모든 조상 클래스의 모든 public 필드를 반환
     */
    @Test
    public void givenClass_whenGetsPublicFields_thenCorrect() throws ClassNotFoundException{
        Class<?> birdClass = Class.forName("ch9.Bird");
        Field[] fields = birdClass.getFields();

        assertEquals(1, fields.length);
        assertEquals("CATEGORY", fields[0].getName());
    }

    // getField() - 단 하나의 Field 객체만 반환(조상 클래스에 선언되었지만 자식 클래스에는 선언되지 않은 private 필드에는 접근 불가)
    @Test
    public void givenClass_whenGetsPublicFieldByName_thenCorrect() throws ClassNotFoundException, NoSuchFieldException{
        Class<?> birdClass = Class.forName("ch9.Bird");
        Field field = birdClass.getField("CATEGORY");

        assertEquals("CATEGORY", field.getName());
    }

    // 클래스에 선언된 필드를 검사할 수 있다.
    @Test
    public void givenClass_whenGetsDeclaredFields_thenCorrect() throws ClassNotFoundException{
        Class<?> birdClass = Class.forName("ch9.Bird");
        Field[] fields = birdClass.getDeclaredFields();

        assertEquals(1, fields.length);
        assertEquals("walks", fields[0].getName());
    }

    // 필드 이름을 알고 있는 경우
    // 필드 이름을 잘못 입력하거나 존재하지 않는 필드를 입력하면 NoSuchFieldException 발생
    @Test
    public void givenClass_whenGetsFieldsByName_thenCorrect() throws ClassNotFoundException, NoSuchFieldException{
        Class<?> birdClass = Class.forName("ch9.Bird");
        Field field = birdClass.getDeclaredField("walks");

        assertEquals("walks", field.getName());
    }

    // 필드 유형 가져오기
    @Test
    public void givenClassField_whenGetsType_thenCorrect() throws ClassNotFoundException, NoSuchFieldException{
        Field field = Class.forName("ch9.Bird")
                .getDeclaredField("walks");
        Class<?> fieldClass = field.getType();

        assertEquals("boolean", fieldClass.getSimpleName());
    }

    /*
    필드값에 접근하고 수정하기
    - 필드값을 가져오려면, 먼저 Field 객체에서 setAccessible 메서드를 호출하여 접근 가능하도록 설정하고 boolean값 true를 전달
     */
    @Test
    public void givenClassField_whenSetsAndGetsValue_thenCorrect() throws Exception{
        Class<?> birdClass = Class.forName("ch9.Bird");
        Bird bird = (Bird) birdClass.getConstructor().newInstance();
        Field field = birdClass.getDeclaredField("walks");
        field.setAccessible(true);

        assertFalse(field.getBoolean(bird));
        assertFalse(bird.walks());

        field.set(bird, true);

        assertTrue(field.getBoolean(bird));
        assertTrue(bird.walks());
    }

    @Test
    public void givenClassField_whenGetsAndSetsWithNull_thenCorrect() throws Exception{
        Class<?> birdClass = Class.forName("ch9.Bird");
        Field field = birdClass.getField("CATEGORY");
        field.setAccessible(true);

        assertEquals("domestic", field.get(null));
    }

    @Test
    public void givenClass_whenGetsAllPublicMethods_thenCorrect() throws Exception{
        Class<?> birdClass = Class.forName("ch9.Bird");
        Method[] methods = birdClass.getMethods();
        List<String> methodNames = getMethodNames(methods);

        assertTrue(methodNames.containsAll(Arrays
                .asList("equals", "notifyAll", "hashCode",
                        "walks", "eats", "toString")));
    }

    @Test
    public void givenClass_whenGetsOnlyDeclaredMethods_thenCorrect() throws ClassNotFoundException{
        Class<?> birdClass = Class.forName("ch9.Bird");
        List<String> actualMethodNames
                = getMethodNames(birdClass.getDeclaredMethods());

        List<String> expectedMethodNames = Arrays
                .asList("setWalks", "walks", "getSound", "eats");

        assertEquals(expectedMethodNames.size(), actualMethodNames.size());
        assertTrue(expectedMethodNames.containsAll(actualMethodNames));
        assertTrue(actualMethodNames.containsAll(expectedMethodNames));
    }

    @Test
    public void givenMethodName_whenGetsMethod_thenCorrect() throws Exception {
        Bird bird = new Bird();
        Method walksMethod = bird.getClass().getDeclaredMethod("walks");
        Method setWalksMethod = bird.getClass().getDeclaredMethod("setWalks", boolean.class);

        assertTrue(walksMethod.canAccess(bird));
        assertTrue(setWalksMethod.canAccess(bird));
    }

    @Test
    public void givenMethod_whenInvokes_thenCorrect() throws Exception{
        Class<?> birdClass = Class.forName("ch9.Bird");
        Bird bird = (Bird) birdClass.getConstructor().newInstance();
        Method setWalksMethod = birdClass.getDeclaredMethod("setWalks", boolean.class);
        Method walksMethod = birdClass.getDeclaredMethod("walks");
        boolean walks = (boolean) walksMethod.invoke(bird);

        assertFalse(walks);
        assertFalse(bird.walks());

        setWalksMethod.invoke(bird, true);

        boolean walks2 = (boolean) walksMethod.invoke(bird);
        assertTrue(walks2);
        assertTrue(bird.walks());
    }

    private static List<String> getMethodNames(Method[] methods) {
        List<String> methodNames = new ArrayList<>();
        for (Method method : methods)
            methodNames.add(method.getName());
        return methodNames;
    }
}

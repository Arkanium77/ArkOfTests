package team.isaz.framework;

import java.util.function.Consumer;

/**
 * <b>Класс @code{ArkOfAsserts}</b>
 * содержит различные ассерты для тестирования при помощи ArkOfTests
 */
public class ArkOfAsserts {

    public static void assertEquals(Object first, Object second) {
        if (!first.equals(second)) {
            throw new AssertResult(first, second, false);
        }
        throw new AssertResult(first, second, true);
    }

    public static void assertTrue(Object object) {
        if (!object.equals(true)) {
            throw new AssertResult(object, true, false);
        }
        throw new AssertResult(object, true, true);
    }

    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertResult(object, "Not Null", false);
        }
        throw new AssertResult(object, null, true);
    }

    public static void assertException(Exception expectedException, Consumer<Object> consumer, Object testingObject) {
        try {
            consumer.accept(testingObject);
        } catch (Exception e) {
            if (e.getClass().equals(expectedException.getClass())) {
                throw new AssertResult(e, expectedException, true);
            }
        }
        throw new AssertResult(testingObject, expectedException, false);
    }
}

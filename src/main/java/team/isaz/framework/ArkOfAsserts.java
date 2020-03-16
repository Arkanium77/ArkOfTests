package team.isaz.framework;

import java.util.function.Consumer;

/**
 * <b>Класс @code{ArkOfAsserts}</b>
 * содержит различные ассерты для тестирования при помощи ArkOfTests
 */
public class ArkOfAsserts {

    public static void assertEquals(Object first, Object second) {
        if (!first.equals(second)) {
            throw new AssertionError();
        }
    }

    public static void assertTrue(Object object) {
        if (!object.equals(true)) {
            throw new AssertionError();
        }
    }

    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError();
        }
    }

    public static void assertException(Exception expectedException, Consumer<Object> consumer, Object testingObject) {
        try {
            consumer.accept(testingObject);
        } catch (Exception e) {
            if (e.getClass().equals(expectedException.getClass())) {
                return;
            }
        }
        throw new AssertionError();
    }
}

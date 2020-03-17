package team.isaz.framework;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.isaz.annotations.After;
import team.isaz.annotations.Before;
import team.isaz.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <b>Класс @code{ArkOfTests}</b>
 * Единственный доступный извне метод @link{ArkOfTests#execute}
 * запускает тестирование всех аннотированных методов.
 * Сами тесты должны запускать ассерты из класса @link{ArkOfAsserts}
 * <p>
 * В ходе поиска тестов анализируются все классы системного загрузчика, с помощью Google Guava.
 */
@SuppressWarnings("rawtypes")
class ArkOfTests {
    Logger logger = LoggerFactory.getLogger(ArkOfTests.class);

    /**
     * <b>Запустить все тесты из пакета</b>
     *
     * @param testingPackage пакет, содержащий файлы для тестирования.
     */
    protected void execute(String testingPackage) {
        var classList = new PackageAnalyzer(testingPackage).getClasses();
        if (classList == null) {
            logger.error("Не удалось прочитать пакет {}", testingPackage);
            return;
        }

        for (var aClass : classList) {
            Map<String, AssertResult> results = runClassTesting(aClass);
            if (results == null || results.size() == 0) continue;
            TestResultLogger resultLogger = new TestResultLogger(aClass.getName(), results);
            resultLogger.loggingTestResults();
        }
    }


    /**
     * <b>Протестировать класс</b>
     *
     * @param aClass класс для тестирования.
     * @return @code{HashMap<String, AssertResult>} содержащий имена короткие имена методов и результаты их тестирования.
     */
    private Map<String, AssertResult> runClassTesting(Class aClass) {
        Object invoker;
        try {
            invoker = aClass.getConstructor().newInstance();
        } catch (Exception e) {
            logger.error("Can't create object of testing class.\nError:{}", e.toString());
            return null;
        }
        List<Method> beforeTest = getAnnotatedMethods(aClass, Before.class);
        List<Method> afterTest = getAnnotatedMethods(aClass, After.class);
        List<Method> theTest = getAnnotatedMethods(aClass, Test.class);
        Map<String, AssertResult> resultMap = testMethodsFromList(theTest, beforeTest, afterTest, invoker);
        return resultMap;
    }

    /**
     * <b>Тестирование списка методов </b>
     *
     * @param tests      методы-тесты
     * @param beforeTest методы, запускающиеся перед каждым тестом
     * @param afterTest  методы, запускающиеся после каждого теста
     * @param invoker    объект, от которого будут вызваны тесты.
     *                   Если тесты записаны в статической форме - используйте null
     * @return @code{HashMap<String,Boolean>} содержащий имена короткие имена методов и результаты их тестирования.
     */
    private Map<String, AssertResult> testMethodsFromList(List<Method> tests, List<Method> beforeTest,
                                                          List<Method> afterTest, Object invoker) {
        Map<String, AssertResult> resultsOfTestSeries = new HashMap<>();
        for (Method test : tests) {
            AssertResult result = testRun(test, beforeTest, afterTest, invoker);
            resultsOfTestSeries.put(test.getName(), result);
        }
        return resultsOfTestSeries;
    }

    /**
     * <b>Получить список меттодов с конкретной аннотацией</b>
     *
     * @param testClass класс, в котором мы ищем аннотированные методы.
     * @param annotate  аннотация, по которой фильтруем методы.
     * @return список методов с данной аннотацией.
     */
    private List<Method> getAnnotatedMethods(Class<?> testClass, Class annotate) {
        return Arrays.stream(testClass.getMethods())
                .filter(method -> method.getAnnotation(annotate) != null)
                .collect(Collectors.toList());
    }

    /**
     * <b>Тестирование метода</b>
     *
     * @param test       метод для тестирования
     * @param beforeTest методы, запускающиеся перед каждым тестом
     * @param afterTest  методы, запускающиеся после каждого теста
     * @param invoker    объект, от которого будут вызваны методы.
     *                   Если тесты записаны в статической форме - используйте null
     * @return AssertedResult, содержащий информацию о результатах теста или ошибке его прервавшей.
     */
    private AssertResult testRun(Method test, List<Method> beforeTest, List<Method> afterTest, Object invoker) {
        AssertResult result = null;
        beforeTest.forEach(method -> invokeThat(method, invoker));
        try {
            test.invoke(invoker);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof AssertResult) {
                result = (AssertResult) e.getTargetException();
            } else {
                result = new AssertResult(e.getTargetException());
            }
        } catch (Throwable e) {
            result = new AssertResult(e);
        } finally {
            afterTest.forEach(method -> invokeThat(method, invoker));
        }
        return result;
    }

    /**
     * <b>Оболочка для вызова метода</b>
     * спасает от лишних try-catch блоков.
     *
     * @param method  вызываемый метод
     * @param invoker объект, от которого вызывается метод.
     *                Если тесты записаны в статической форме - используйте null
     */
    private void invokeThat(Method method, Object invoker) {
        try {
            method.invoke(invoker);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("Не удалось вызвать метод! \n Error: {}", e.toString());
        }
    }
}

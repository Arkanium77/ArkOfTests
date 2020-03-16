package team.isaz.framework;


import com.google.common.base.Strings;
import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.isaz.annotations.After;
import team.isaz.annotations.Before;
import team.isaz.annotations.Test;
import team.isaz.exceptions.PackageNotFoundException;
import team.isaz.utils.StaticUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class ArkOfTests {
    static Logger logger = LoggerFactory.getLogger(ArkOfTests.class);

    /**
     * <b>Запустить все тесты из пакета</b>
     *
     * @param testingPackage пакет, содержащий файлы для тестирования.
     * @throws PackageNotFoundException если не удалось загрузить пакет.
     */
    public static void execute(String testingPackage) throws PackageNotFoundException {
        var classList1 = getClasses(testingPackage);
        if (classList1 == null) {
            throw new PackageNotFoundException("Не удалось загрузить пакет.");
        }

        for (var aClass : classList1) {
            Map<String, Boolean> results = testClass(aClass);
            if (results == null || results.size() == 0) continue;

            loggingTestResults(aClass.getName(), results);
        }
    }

    /**
     * <b>Логгирование результатов тестов</b>
     *
     * @param className имя тестируемого класса
     * @param results   HashMap, содержащий имена методов и результат теста.
     */
    private static void loggingTestResults(String className, Map<String, Boolean> results) {
        int maxNameOfMethodLength = StaticUtils.getMaxStringLength(results.keySet()) + 4;
        int fullStringLength = 26 + maxNameOfMethodLength;
        logger.info(StaticUtils.centredString(String.format("%s%s%s", "Testing of ", className, " class"),
                fullStringLength, '-'));
        for (String test : results.keySet()) {
            String presenting = StaticUtils.getPresentingString(results.get(test));
            logger.info("    Test     {} is {}", Strings.padEnd(test, maxNameOfMethodLength, ' '), presenting);
        }
        logger.info(StaticUtils.centredString("END OF TEST",
                fullStringLength, '-') + '\n');
    }

    /**
     * <b>Протестировать класс</b>
     *
     * @param aClass класс для тестирования.
     * @return @code{HashMap<String,Boolean>} содержащий имена короткие имена методов и результаты их тестирования.
     */
    private static Map<String, Boolean> testClass(Class aClass) {
        Object invoker;
        try {
            invoker = aClass.getConstructor().newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        List<Method> beforeTest = getAnnotatedMethods(aClass, Before.class);
        List<Method> afterTest = getAnnotatedMethods(aClass, After.class);
        List<Method> theTest = getAnnotatedMethods(aClass, Test.class);
        Map<String, Boolean> mapOfResults = testMethodsFromList(theTest, beforeTest, afterTest, invoker);
        return mapOfResults;
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
    protected static Map<String, Boolean> testMethodsFromList(List<Method> tests, List<Method> beforeTest,
                                                              List<Method> afterTest, Object invoker) {
        Map<String, Boolean> resultsOfTestSeries = new HashMap<>();
        for (Method test : tests) {
            boolean result = testRun(test, beforeTest, afterTest, invoker);
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
    protected static List<Method> getAnnotatedMethods(Class<?> testClass, Class annotate) {
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
     * @return true, если тест успешно пройден и false во всех остальных случаях.
     */
    protected static boolean testRun(Method test, List<Method> beforeTest, List<Method> afterTest, Object invoker) {
        beforeTest.forEach(method -> invokeThat(method, invoker));
        try {
            test.invoke(invoker);
            afterTest.forEach(method -> invokeThat(method, invoker));
            return true;
        } catch (Exception e) {
            afterTest.forEach(method -> invokeThat(method, invoker));
            return false;
        }

    }

    /**
     * <b>Оболочка для вызова метода</b>
     * спасает от лишних try-catch блоков.
     *
     * @param method  вызываемый метод
     * @param invoker объект, от которого вызывается метод.
     *                Если тесты записаны в статической форме - используйте null
     * @return результат выполнения метода или null, в случае ошибок.
     */
    protected static Object invokeThat(Method method, Object invoker) {
        try {
            return method.invoke(invoker);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <b>Получение классов из пакета</b>
     *
     * @param testingPackage пакет, в котором ищем классы
     * @return @code{List<Class>}, содержащий все классы из пакета testingPackage или null, в случае ошибок чтения.
     */
    protected static List<Class> getClasses(String testingPackage) {
        ClassPath classpath = getClassPath();
        if (classpath == null) return null;
        List<Class> classes;
        classes = classpath.getTopLevelClassesRecursive(testingPackage)
                .stream()
                .map(classInfo -> {
                    try {
                        return Class.forName(classInfo.getName());
                    } catch (Exception e) {
                        logger.error("Can't load class!\n" + e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toList());
        return classes;
    }

    /**
     * <b>Получить classpath</b>
     *
     * @return classpath из текущего SystemClassLoader
     */
    private static ClassPath getClassPath() {
        final ClassLoader loader = ClassLoader.getSystemClassLoader();
        ClassPath classpath;
        try {
            classpath = ClassPath.from(loader); // scans the class path used by classloader
        } catch (Exception e) {
            return null;
        }
        return classpath;
    }
}

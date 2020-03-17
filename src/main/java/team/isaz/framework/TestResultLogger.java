package team.isaz.framework;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class TestResultLogger {
    private static final String INDENT = "    ";
    private Logger logger = LoggerFactory.getLogger(TestResultLogger.class);
    private String className;
    private Map<String, AssertResult> results;

    /**
     * <b>Приватный конструктор</b>
     * Чтоб не было возможности создать объект с конктруктором по умолчанию,
     * воизбежении ошибок.
     */
    private TestResultLogger() {

    }

    protected TestResultLogger(String className, Map<String, AssertResult> results) {
        this.className = className;
        this.results = new HashMap<>();
        results.forEach((key, value) -> this.results.put(key, value));
    }

    /**
     * <b>Логгирование результатов тестов</b>
     */
    protected void loggingTestResults() {
        int maxNameOfMethodLength = getMaxStringLength(results.keySet()) + 4;
        int fullStringLength = 26 + maxNameOfMethodLength;
        logger.info(centredString(String.format("%s%s%s", "Testing of ", className, " class"),
                fullStringLength, '-'));
        for (String test : results.keySet()) {
            String presenting = getTestResultLogString(maxNameOfMethodLength, test);
            logger.info(presenting);
        }
        String log = getEndOfTestSeriesLogString(fullStringLength);
        logger.info(log);
    }

    /**
     * <b>Генерация результирующей строки</b>
     *
     * @param fullStringLength длина предыдущих строк (для выравнивания)
     * @return строку содержащую информацию о числе пройденных\общем числе тестов
     */
    private String getEndOfTestSeriesLogString(int fullStringLength) {
        long testPassed = results.values()
                .stream()
                .map(AssertResult::getResultOfAssertion)
                .filter(result -> result).count();
        return centredString("TEST PASSED " + testPassed + " OF " + results.size(),
                fullStringLength, '-') + '\n';
    }

    /**
     * <b>Генерация логгируемой строки для каждого результата.</b>
     *
     * @param maxNameOfMethodLength максимальная длина имени метода (для выравнивания)
     * @param test                  имя теста, для которого генерируем строку
     * @return строку, пригодную для отправки в лог.
     */
    private String getTestResultLogString(int maxNameOfMethodLength, String test) {
        AssertResult result = results.get(test);
        String presenting = getPresentationOfResultStatus(result);
        presenting = String.format(presenting, Strings.padEnd(test, maxNameOfMethodLength, ' '));
        if (result.isTestFailed()) {
            presenting += "\n" + getExceptedActual(result, presenting.length());
        }
        return presenting;
    }

    /**
     * <b>Текстовое представление статуса теста</b>
     *
     * @param result результат теста
     * @return строку, содержащую результат теста или причину его невыполнения.
     */
    private String getPresentationOfResultStatus(AssertResult result) {
        if (result.isTestWasInterrupt()) {
            return INDENT + "Test" + INDENT + "%s" + INDENT + "was interrupt by " + result.getUnexpectedThrowable().toString();
        }
        String presenting = INDENT + "Test" + INDENT + "%s" + INDENT +
                "is " + getBooleanString(result.getResultOfAssertion());
        return presenting;
    }

    /**
     * <b>Текстовое представление ожидаемый-полученный</b>
     *
     * @param result              результат теста
     * @param additionalIndentLen длина основной строки вывода
     * @return текстовое представление Expected-Actual с большим отступом для вывода.
     */
    private String getExceptedActual(AssertResult result, int additionalIndentLen) {
        String indent = repeat(INDENT, 6) + repeat(" ", additionalIndentLen);
        String presenting = indent + "Expected: " + result.getExpectedObject().toString() + '\n';
        presenting += indent + "Actual  : " + result.getAssertedObject().toString();
        return presenting;
    }

    /**
     * <b>Строковое представление true\false для тестов</b>
     *
     * @param value результат теста
     * @return составную часть строки для вывода результата.
     */
    private String getBooleanString(Boolean value) {
        String presenting = "";
        if (value) {
            presenting = "passed +";
        } else {
            presenting = "failed -";
        }
        return presenting;
    }

    /**
     * <b>Вычисление максимальной длины строки</b>
     * Необходимо для форматируемого вывода в три колонки.
     *
     * @param uniqueStrings сет кникальных строк (названий методов)
     * @return наибольшую длину строки.
     */
    private int getMaxStringLength(Set<String> uniqueStrings) {
        return uniqueStrings.stream()
                .mapToInt(String::length)
                .max().orElse(0);
    }

    /**
     * <b>Центрирование строки</b>
     *
     * @param string     строка для центрирования.
     * @param fullLength длина для дополнения символами
     * @param filler     символ-заполнитель.
     * @return строку, дополненную до нужной длины слева и справа символом filler.
     */
    private String centredString(String string, int fullLength, char filler) {
        if (string.length() > fullLength) return string;

        fullLength -= string.length();
        fullLength = (fullLength + (fullLength % 2)) / 2;
        char[] chars = new char[fullLength];
        Arrays.fill(chars, filler);
        String pad = new String(chars);
        return String.format("%s%s%s", pad, string, pad);
    }

    /**
     * <b>Умножение строк</b>
     * не такое красивое как в питоне
     *
     * @param string повторяемая строка
     * @param count  сколько раз повторить
     * @return строку, в которой подстрока string содержится count раз
     */
    private String repeat(String string, int count) {
        String repeatedString = "";
        while (count > 0) {
            repeatedString += string;
            count--;
        }
        return repeatedString;
    }
}

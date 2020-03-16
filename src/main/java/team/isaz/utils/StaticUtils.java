package team.isaz.utils;

import java.util.Arrays;
import java.util.Set;

public class StaticUtils {
    /**
     * <b>Строковое представление true\false для тестов</b>
     *
     * @param value результат теста
     * @return составную часть строки для вывода результата.
     */
    public static String getPresentingString(Boolean value) {
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
    public static int getMaxStringLength(Set<String> uniqueStrings) {
        return uniqueStrings.stream()
                .mapToInt(String::length)
                .max().orElse(0);
    }


    /**
     * <b>Центрирование строки</b>
     * @param string строка для центрирования.
     * @param fullLength длина для дополнения символами
     * @param filler символ-заполнитель.
     * @return строку, дополненную до нужной длины слева и справа символом filler.
     */
    public static String centredString(String string, int fullLength, char filler) {
        if (string.length() > fullLength) return string;

        fullLength -= string.length();
        fullLength = (fullLength + (fullLength % 2)) / 2;
        char[] chars = new char[fullLength];
        Arrays.fill(chars, filler);
        String pad = new String(chars);
        return String.format("%s%s%s", pad, string, pad);

    }
}

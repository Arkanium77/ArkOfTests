package team.isaz.exampleClasses.another;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divider {
    /**
     * <b>Деление чисел</b>
     *
     * @param number  делимое (любого типа).
     * @param divider делитель (любого типа).
     * @return результат деления между BigDecimal версиями делимого и делителя.
     * @throws ArithmeticException в случае попытки деления на ноль.
     */
    public Number div(Number number, Number divider) {
        BigDecimal universalDivider = new BigDecimal(divider.toString());
        if (universalDivider.floatValue() == 0) {
            throw new ArithmeticException("Делитель не может быть равен нулю");
        }
        BigDecimal universalNumber = new BigDecimal(number.toString());
        return universalNumber.divide(universalDivider, RoundingMode.HALF_UP);
    }

}

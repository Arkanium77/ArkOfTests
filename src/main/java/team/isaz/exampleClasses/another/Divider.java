package team.isaz.exampleClasses.another;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divider {
    public Number div(Number number, Number divider) {
        BigDecimal universalDivider = new BigDecimal(divider.toString());
        if (universalDivider.floatValue() == 0) {
            throw new ArithmeticException("Делитель не может быть равен нулю");
        }
        BigDecimal universalNumber = new BigDecimal(number.toString());
        return universalNumber.divide(universalDivider, RoundingMode.HALF_UP);
    }

}

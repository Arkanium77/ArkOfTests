package team.isaz.exampleClasses;

import java.math.BigDecimal;

public class Adder {
    /**
     * <b>Сложение чисел</b>
     *
     * @param a число (любого типа)
     * @param b число (любого типа)
     * @return результат вычисления между двумя BigDecimal версиями чисел a и b, что,
     * потенциально, позволяет складывать числа любого размера.
     */
    public Number add(Number a, Number b) {
        return new BigDecimal(a.toString()).add(new BigDecimal(b.toString()));
    }

    /**
     * <b>Сложение массивов</b>
     *
     * @param a массив любого типа
     * @param b массив любого типа
     * @return новый массив типа Object, содержащий все элементы a и, затем, все элементы b
     */
    public Object[] add(Object[] a, Object[] b) {
        Object[] result = new Object[a.length + b.length];
        int i = 0;
        for (Object o : a) {
            result[i] = o;
            i++;
        }
        for (Object o : b) {
            result[i] = o;
            i++;
        }
        return result;
    }
}

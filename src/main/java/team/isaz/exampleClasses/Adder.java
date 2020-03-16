package team.isaz.exampleClasses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adder {
    public Number add(Number a, Number b) {
        return new BigDecimal(a.toString()).add(new BigDecimal(b.toString()));
    }

    public List<Object> add(Collection<Object> a, Collection<Object> b) {
        List<Object> result = new ArrayList<>();
        result.addAll(a);
        result.addAll(b);
        return result;
    }

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

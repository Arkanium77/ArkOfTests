package team.isaz.exampleClassesTests;

import team.isaz.annotations.Before;
import team.isaz.annotations.Test;
import team.isaz.exampleClasses.Adder;
import team.isaz.framework.ArkOfAsserts;

public class AdderTest {
    Adder adder;

    @Before
    public void before() {
        adder = new Adder();
    }

    @Test
    public void add3and5is8() {
        ArkOfAsserts.assertEquals(adder.add(3, 5).intValue(), 8);
    }

    @Test
    public void add3and5is100() {
        ArkOfAsserts.assertEquals(adder.add(3, 5), 100);
    }

    @Test
    public void add3_0000001and0_00000000002is3_00000010002() {
        ArkOfAsserts.assertEquals(adder.add(3.0000001, 0.00000000002).doubleValue(), 3.00000010002);
    }

    @Test
    public void lengthOf3ElemArrayAddedTo5ElemArrayEquals8() {
        Integer[] a = {3, 4, 5};
        Integer[] b = {6, 7, 8, 9, 0};
        ArkOfAsserts.assertTrue(adder.add(a, b).length == 8);
    }

    @Test
    public void addOfTwoExistCollectionsIsNotNull() {
        Integer[] a = {3, 4, 5};
        Integer[] b = {6, 7, 8, 9, 0};
        ArkOfAsserts.assertNotNull(adder.add(a, b));
    }
}

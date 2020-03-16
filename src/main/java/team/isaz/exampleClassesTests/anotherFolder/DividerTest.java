package team.isaz.exampleClassesTests.anotherFolder;

import team.isaz.framework.ArkOfAsserts;
import team.isaz.annotations.Before;
import team.isaz.annotations.Test;
import team.isaz.exampleClasses.another.Divider;

public class DividerTest {
    Divider divider;
    @Before
    public void before(){
        divider = new Divider();
    }

    @Test
    public void divideByZeroException(){
        ArkOfAsserts.assertException(
                new ArithmeticException(),
                (none) -> divider.div(1,0),
                null);
    }

    @Test
    public void divideFiveByTwoisTwoPointFive(){
        ArkOfAsserts.assertEquals(divider.div(5,2).doubleValue(),2.5);
    }


}

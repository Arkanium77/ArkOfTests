package team.isaz.exampleClassesTests.anotherFolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.isaz.annotations.After;
import team.isaz.annotations.Before;
import team.isaz.annotations.Test;
import team.isaz.exampleClasses.another.Divider;
import team.isaz.framework.ArkOfAsserts;

import java.time.LocalTime;

public class DividerTest {
    Logger logger = LoggerFactory.getLogger(DividerTest.class);

    Divider divider;
    String simpleString;

    @Before
    public void beforeFirst() {
        divider = new Divider();
    }

    @Before
    public void beforeSecond() {
        simpleString = "SimpleString is " + LocalTime.now().toString();
    }

    @Test
    public void divideByZeroException() {
        ArkOfAsserts.assertException(
                new ArithmeticException(),
                (none) -> divider.div(1, 0),
                null);
    }

    @Test
    public void divideFiveByTwoIsTwoPointFive() {
        ArkOfAsserts.assertEquals(divider.div(5, 2).doubleValue(), 2.5);
    }

    @Test
    public void testWithStackOverflowException() {
        testWithStackOverflowException();
    }

    @After
    public void after() {
        //Нужен только для демонстрации того, что аннтации Before-After обрабатываются.
        logger.info("it's after metod runs! {}\n", simpleString);
    }


}

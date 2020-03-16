package team.isaz;

import team.isaz.exceptions.PackageAnalyzeException;
import team.isaz.framework.ArkOfTests;

import java.io.IOException;


public class App {

    public static void main(String[] args) throws ClassNotFoundException, IOException, PackageAnalyzeException {
        /*
        Пример работы фреймворка для тестирования.
        Будут протестированны классы AdderTest и DividerTest,
        представляющие собой пакеты тестов для классов Adder и Divider.

        Класс DividerTest помещён в подпакет .anotherFolder для демонстрации того,
        что фреймворк анализирует не только основной пакет, но и все подпакеты.

        DividerTest содержит дополнительное поле simpleString, которое инициализируется
        в одном из @Before методов и выводится в консоль в @After.
        Разумеется, тесты, как и классы абсолютно не практикоприменимы.

        В классе ArkOfTests все методы статические, это связано с тем, как именно
        вызывался метод в примере из презентации, для максимального соответствия.
         */
        ArkOfTests.execute("team.isaz.exampleClassesTests");
    }
}

package team.isaz;

import team.isaz.framework.GatesOfTesting;


public class App {

    public static void main(String[] args) {
        /*
        Пример работы фреймворка для тестирования.
        Будут протестированны классы AdderTest и DividerTest,
        представляющие собой пакеты тестов для классов Adder и Divider.

        Класс DividerTest помещён в подпакет .anotherFolder для демонстрации того,
        что фреймворк анализирует не только основной пакет, но и все подпакеты.

        DividerTest содержит дополнительное поле simpleString, которое инициализируется
        в одном из @Before методов и выводится в консоль в @After.
        Разумеется, тесты, как и классы абсолютно не практикоприменимы.

        Класс GatesOfTesting является статическим интерфейсом для основного класса фреймворка.
         */
        GatesOfTesting.enter("team.isaz.exampleClassesTests");
    }
}

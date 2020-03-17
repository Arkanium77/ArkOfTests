package team.isaz.framework;

public class GatesOfTesting {
    /**
     * <b>Запуск тестирования</b>
     * статический интерфейс для ArkOfTests
     *
     * @param testingPackage пакет для тестирования
     */
    public static void enter(String testingPackage) {
        ArkOfTests ark = new ArkOfTests();
        ark.execute(testingPackage);
    }
}

package team.isaz.framework;

class AssertResult extends AssertionError {
    private Object assertedObject;
    private Object expectedObject;
    private Boolean resultOfAssertion;
    private Throwable unexpectedThrowable;
    private String testName;

    /**
     * <b>Конструктор для штатного выброса результата</b>
     *
     * @param assertedObject    полученный объект
     * @param expectedObject    ожидаемый объект
     * @param resultOfAssertion результат теста
     */
    protected AssertResult(Object assertedObject, Object expectedObject,
                           Boolean resultOfAssertion) {
        this.assertedObject = assertedObject;
        this.expectedObject = expectedObject;
        this.resultOfAssertion = resultOfAssertion;
        this.unexpectedThrowable = null;
        this.testName = "";
    }

    /**
     * <b>Конструктор для нешатных завершений теста</b>
     *
     * @param unexpectedThrowable нечто, вызвавшее досрочное завершение теста.
     */
    protected AssertResult(Throwable unexpectedThrowable) {
        this.resultOfAssertion = false;
        this.unexpectedThrowable = unexpectedThrowable;
        this.testName = "";
    }

    /**
     * <b>Конструктор для нешатных завершений теста</b>
     * с названием вызвавшего метода
     *
     * @param testName            имя теста
     * @param unexpectedThrowable нечто, вызвавшее досрочное завершение теста.
     */
    protected AssertResult(String testName, Throwable unexpectedThrowable) {
        this.testName = testName;
        this.resultOfAssertion = false;
        this.unexpectedThrowable = unexpectedThrowable;
    }


    /**
     * <b>Копирующий конструктор</b>
     *
     * @param other копируемые результаты теста.
     */
    protected AssertResult(AssertResult other) {
        this.assertedObject = other.assertedObject;
        this.expectedObject = other.expectedObject;
        this.resultOfAssertion = other.resultOfAssertion;
        this.unexpectedThrowable = other.unexpectedThrowable;
        this.testName = other.testName;
    }

    /**
     * <b>Копирующий конструктор</b>
     * с переопределением вызвавшего метода.
     *
     * @param testName имя теста
     * @param other    результат теста
     */
    protected AssertResult(String testName, AssertResult other) {
        this.assertedObject = other.assertedObject;
        this.expectedObject = other.expectedObject;
        this.resultOfAssertion = other.resultOfAssertion;
        this.unexpectedThrowable = other.unexpectedThrowable;
        this.testName = testName;
    }

    protected Boolean getResultOfAssertion() {
        return resultOfAssertion;
    }

    protected Object getAssertedObject() {
        return assertedObject;
    }

    protected Object getExpectedObject() {
        return expectedObject;
    }

    protected Throwable getUnexpectedThrowable() {
        return unexpectedThrowable;
    }

    protected boolean isTestWasInterrupt() {
        return unexpectedThrowable != null;
    }

    protected boolean isTestFailed() {
        return !isTestWasInterrupt() && !resultOfAssertion;
    }

    protected String getTestName() {
        return testName;
    }

    @Override
    public String toString() {
        if (unexpectedThrowable != null) {
            return "Test " + testName + " was interrupt by " + unexpectedThrowable.toString();
        }
        return "Test " + testName + " result is " + resultOfAssertion.toString() +
                "\n    Expected:" + expectedObject.toString() +
                "\n    Actual: " + assertedObject.toString();
    }

}

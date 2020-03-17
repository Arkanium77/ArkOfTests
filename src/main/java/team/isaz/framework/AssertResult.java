package team.isaz.framework;

class AssertResult extends AssertionError {
    private Object assertedObject;
    private Object expectedObject;
    private Boolean resultOfAssertion;
    private Throwable unexpectedThrowable;

    /**
     * Конструктор для штатного выброса результата
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
        unexpectedThrowable = null;
    }

    /**
     * Конструктор для нешатных завершений теста
     *
     * @param unexpectedThrowable нечто, вызвавшее досрочное завершение теста.
     */
    protected AssertResult(Throwable unexpectedThrowable) {
        this.resultOfAssertion = false;
        this.unexpectedThrowable = unexpectedThrowable;
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

    @Override
    public String toString() {
        if (unexpectedThrowable != null) {
            return "Test was interrupt by " + unexpectedThrowable.toString();
        }
        return "\nExpected:" + expectedObject.toString() +
                "\nActual: " + assertedObject.toString() +
                "\n    => Test result is " + resultOfAssertion.toString();
    }

}

package team.isaz.exceptions;

/**
 * Исключение, возникающее в случае ошибок анализа пакета,
 * например, если он не найден или
 * произошла неудачная попытка загрузть класс ему принадлежащий.
 */
public class PackageAnalyzeException extends Exception {
    public PackageAnalyzeException(String message) {
        super(message);
    }

    public PackageAnalyzeException() {
        super();
    }
}

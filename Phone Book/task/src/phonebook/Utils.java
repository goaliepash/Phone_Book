package phonebook;

/**
 * Класс со всякими полезными функциями для проекта.
 *
 * @author Иванов Павел Александрович
 */
public class Utils {

    /**
     * Функция, возвращающее время в строковом представлении
     *
     * @param time время в миллисекундах
     * @return время в строковом представлении [1 min. 20 sec. 39 ms.]
     */
    public static String getTimeAsString(long time) {
        long minutes = time / 60000;
        long seconds = (time - minutes * 60000) / 1000;
        long milliSeconds = time - seconds * 1000;
        return String.format("%d min. %d sec. %d ms.", minutes, seconds, milliSeconds);
    }
}

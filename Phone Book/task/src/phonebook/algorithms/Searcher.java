package phonebook.algorithms;

/**
 * Класс, содержащий функции для поиска элементов в массиве различными методами.
 *
 * @author Иванов Павел Александрович
 */
public class Searcher {

    public static boolean linearSearchItem(String[] array, String item) {
        for (String s : array) {
            if (s.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для поиска элемента блочным способом в массиве строк.
     *
     * @param array массив строк
     * @param item  искомый элемент
     * @return true, если элемент найден, иначе - false
     */
    public static boolean jumpSearchItem(String[] array, String item) {
        int blockSize = (int) Math.floor(Math.sqrt(array.length));
        int i = 0;
        while (i < array.length) {
            if (i == 0 && array[i].equals(item)) {
                return true;
            } else if (array[i].compareTo(item) >= 0) {
                for (int j = i; j > i - blockSize; j--) {
                    if (array[j].equals(item)) {
                        return true;
                    }
                }
            }
            if (i + blockSize < array.length) {
                i += blockSize;
            } else {
                i = array.length - 1;
            }
        }
        return false;
    }

    /**
     * Метод для бинарного поиска строки в массиве строк.
     *
     * @param array исходный массив
     * @param item  искомая строка
     * @return индекс искомой строки в массиве (если элемент не найден возвращается -1)
     */
    public static int binarySearch(String[] array, String item) {
        int index = -1;
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) / 2);
            if (array[mid].compareTo(item) < 0) {
                low = mid + 1;
            } else if (array[mid].compareTo(item) > 0) {
                high = mid - 1;
            } else if (array[mid].equals(item)) {
                index = mid;
                break;
            }

        }
        return index;
    }
}

package phonebook.algorithms;

/**
 * Класс содержит набор методов сортировок массивов.
 *
 * @author Иванов Павел Александрович
 */
public class Sorter {

    /**
     * Сортировка массива строк пузырьковым методом.
     * Если время сортировки превосходит лимит - выйти из метода.
     *
     * @param array     массив строк
     * @param timeLimit ограничение по времени
     * @return true, если сортировка выполнена, иначе - false
     */
    public static boolean bubble(String[] array, long timeLimit) {
        long startingSortingTime = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            long currentTime = System.currentTimeMillis() - startingSortingTime;
            if (currentTime > timeLimit) {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод быстрой сортировки массива строк.
     *
     * @param array исходный массив строк
     */
    public static void quick(String[] array, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(array, begin, end);
            quick(array, begin, partitionIndex - 1);
            quick(array, partitionIndex + 1, end);
        }
    }

    private static int partition(String[] array, int begin, int end) {
        String pivot = array[end];
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                String swapTemp = array[i];
                array[i] = array[j];
                array[j] = swapTemp;
            }
        }
        String swapTemp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = swapTemp;
        return i + 1;
    }
}

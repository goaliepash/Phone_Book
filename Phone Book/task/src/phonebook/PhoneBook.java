package phonebook;

import phonebook.algorithms.Searcher;
import phonebook.algorithms.Sorter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс для поиска имён в телефонной книге различными способами.
 *
 * @author Иванов Павел Александрович
 */
public class PhoneBook {

    private static final String START_LINEAR_SEARCHING_LABEL = "Start searching (linear search)...";
    private static final String START_BUBBLE_JUMPING_SEARCHING_LABEL = "Start searching (bubble sort + jump search)...";
    private static final String START_QUICK_BINARY_SEARCHING_LABEL = "Start searching (quick sort + binary search)...";
    private static final String START_HASH_TABLE_SEARCHING_LABEL = "Start searching (hash table)...";

    private static long linearSearchTime;

    /**
     * Поиск имён в телефонной книге методом линейного поиска.
     *
     * @param directory исходная телефонная книга в виде строкового массива
     * @param find      массив с искомыми элементами
     */
    public static void linearSearch(String[] directory, String[] find) {
        System.out.println(START_LINEAR_SEARCHING_LABEL);
        String[] directoryWithoutNumber = deleteNumbersFromCatalog(directory);
        long startTime = System.currentTimeMillis();
        int countOfFoundItems = searchForNumberOfItems(directoryWithoutNumber, find, SearchWay.LINEAR);
        int countOfSearchedItems = find.length;
        long endTime = System.currentTimeMillis();
        linearSearchTime = endTime - startTime;
        printLinearSearchInfo(countOfFoundItems, countOfSearchedItems);
    }

    /**
     * Поиск имён в отсортированной пузырьковым методом телефонной книге.
     * Поиск выполняется с помощью блочного метода (jumping search)
     *
     * @param directory исходная телефонная книга в виде строкового массива
     * @param find      массив с искомыми элементами
     */
    public static void jumpBubbleSearch(String[] directory, String[] find) {
        System.out.println(START_BUBBLE_JUMPING_SEARCHING_LABEL);
        String[] directoryWithoutNumber = deleteNumbersFromCatalog(directory);
        // Сортировка
        long startSortingTime = System.currentTimeMillis();
        int countOfSearchedItems = find.length;
        /*
        Если пузырьковая сортировка выполнена (быстро), то переходим к блочному поиску.
        Иначе - переключаемся на линейный поиск.
        */
        long bubbleSortTime;
        if (Sorter.bubble(directoryWithoutNumber, linearSearchTime * 10)) {
            long endSortingTime = System.currentTimeMillis();
            bubbleSortTime = endSortingTime - startSortingTime;
            // Блочный поиск
            long startSearchingTime = System.currentTimeMillis();
            int countOfFoundItems = searchForNumberOfItems(directoryWithoutNumber, find, SearchWay.JUMP);
            long endSearchingTime = System.currentTimeMillis();
            long jumpSearchTime = endSearchingTime - startSearchingTime;
            printSortAndSearchInfo(countOfFoundItems, countOfSearchedItems, bubbleSortTime, jumpSearchTime, false);
        } else {
            long endSortingTime = System.currentTimeMillis();
            bubbleSortTime = endSortingTime - startSortingTime;
            // Линейный поиск
            long startSearchingTime = System.currentTimeMillis();
            int countOfFoundItems = searchForNumberOfItems(directoryWithoutNumber, find, SearchWay.LINEAR);
            long endSearchingTime = System.currentTimeMillis();
            linearSearchTime = endSearchingTime - startSearchingTime;
            printSortAndSearchInfo(countOfFoundItems, countOfSearchedItems, bubbleSortTime, linearSearchTime, true);
        }
    }

    /**
     * Поиск имён в отсортированном методом быстрой сортировки телефонной книге.
     * Поиск производится бинарным методом (binary search).
     *
     * @param directory исходная телефонная книга в виде строкового массива
     * @param find      массив с искомыми элементами
     */
    public static void quickSortBinarySearch(String[] directory, String[] find) {
        System.out.println(START_QUICK_BINARY_SEARCHING_LABEL);
        String[] directoryWithoutNumbers = deleteNumbersFromCatalog(directory);
        // Сортировка
        long startQuickSortTime = System.currentTimeMillis();
        Sorter.quick(directoryWithoutNumbers, 0, directoryWithoutNumbers.length - 1);
        long endQuickSortTime = System.currentTimeMillis();
        long quickSortTime = endQuickSortTime - startQuickSortTime;
        // Поиск
        long startBinarySearchTime = System.currentTimeMillis();
        int countOfFoundItems = searchForNumberOfItems(directoryWithoutNumbers, find, SearchWay.BINARY);
        long endBinarySearchTime = System.currentTimeMillis();
        long binarySearchTime = endBinarySearchTime - startBinarySearchTime;
        printSortAndSearchInfo(countOfFoundItems, find.length, quickSortTime, binarySearchTime, false);
    }

    /**
     * Поиск имён в телефонной книге, преобразованной в хэш-таблицу.
     *
     * @param directory исходная телефонная книга в виде строкового массива
     * @param find      массив с искомыми элементами
     */
    public static void hashTableSearch(String[] directory, String[] find) {
        System.out.println(START_HASH_TABLE_SEARCHING_LABEL);
        String[] directoryWithoutNumber = deleteNumbersFromCatalog(directory);
        // Создание
        long startCreatingHashTableTime = System.currentTimeMillis();
        Set<String> hashSet = new HashSet<>(Arrays.asList(directoryWithoutNumber));
        long endCreatingHashTableTime = System.currentTimeMillis();
        long creatingHashTableTime = endCreatingHashTableTime - startCreatingHashTableTime;
        // Поиск
        long startSearchingHashTableTime = System.currentTimeMillis();
        int countOfFoundItems = 0;
        for (String item : find) {
            if (hashSet.contains(item)) {
                countOfFoundItems++;
            }
        }
        long endSearchingHashTableTime = System.currentTimeMillis();
        long searchingHashTableTime = endSearchingHashTableTime - startSearchingHashTableTime;
        printHashTableCreatingAndSearchingTime(countOfFoundItems, find.length, creatingHashTableTime, searchingHashTableTime);
    }

    private static int searchForNumberOfItems(String[] directory, String[] find, SearchWay way) {
        int countOfFoundItems = 0;
        for (String s : find) {
            switch (way) {
                case LINEAR:
                    if (Searcher.linearSearchItem(directory, s)) {
                        countOfFoundItems++;
                    }
                    break;
                case JUMP:
                    if (Searcher.jumpSearchItem(directory, s)) {
                        countOfFoundItems++;
                    }
                    break;
                case BINARY:
                    if (Searcher.binarySearch(directory, s) != -1) {
                        countOfFoundItems++;
                    }
                    break;
                default:
                    break;
            }
        }
        return countOfFoundItems;
    }

    private static String[] deleteNumbersFromCatalog(String[] catalog) {
        String[] catalogWithoutNumbers = new String[catalog.length];
        for (int i = 0; i < catalog.length; i++) {
            StringBuilder sb = new StringBuilder(catalog[i]);
            sb.replace(0, sb.indexOf(" ") + 1, "");
            catalogWithoutNumbers[i] = sb.toString();
        }
        return catalogWithoutNumbers;
    }

    private static void printLinearSearchInfo(int countOfFoundItems, int countOfSearchedItems) {
        String time = Utils.getTimeAsString(linearSearchTime);
        System.out.printf("Found %d / %d entries. Time taken: %s%n%n", countOfFoundItems, countOfSearchedItems, time);
    }

    private static void printSortAndSearchInfo(
            int countOfFoundItems,
            int countOfSearchedItems,
            long sortTime,
            long searchTime,
            boolean isBubbleSortStopped) {
        String sortTimeString = Utils.getTimeAsString(sortTime);
        String searchTimeString = Utils.getTimeAsString(searchTime);
        String totalTimeString = Utils.getTimeAsString(sortTime + searchTime);
        System.out.printf(
                "Found %d / %d entries. Time taken: %s%n",
                countOfFoundItems,
                countOfSearchedItems,
                totalTimeString);
        if (!isBubbleSortStopped) {
            System.out.printf("Sorting time: %s%n", sortTimeString);
        } else {
            System.out.printf("Sorting time: %s - STOPPED, moved to linear search%n", sortTimeString);
        }
        System.out.printf("Searching time: %s%n%n", searchTimeString);
    }

    private static void printHashTableCreatingAndSearchingTime(
            int countOfFoundItems,
            int countOfSearchingItems,
            long creatingHashTableTime,
            long searchingHashTableTime) {
        String creatingHashTableTimeString = Utils.getTimeAsString(creatingHashTableTime);
        String searchingHashTableTimeString = Utils.getTimeAsString(searchingHashTableTime);
        String totalTimeString = Utils.getTimeAsString(creatingHashTableTime + searchingHashTableTime);
        System.out.printf("Found %d / %d entries. Time taken: %s%n", countOfFoundItems, countOfSearchingItems, totalTimeString);
        System.out.printf("Creating time: %s%n", creatingHashTableTimeString);
        System.out.printf("Searching time: %s%n%n", searchingHashTableTimeString);
    }
}

package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс, содержащий функции для чтения данных из файла.
 *
 * @author Иванов Павел Александрович
 */
public class DataReader {

    /**
     * Функция для чтения данных из файла в строковый массив
     *
     * @param sourceFile исходный файл
     * @return строковый массив с данными
     */
    public static String[] readStringsFromFile(File sourceFile) {
        List<String> listData = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(sourceFile);
            while (scanner.hasNext()) {
                listData.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] arrayData = new String[listData.size()];
        return listData.toArray(arrayData);
    }
}

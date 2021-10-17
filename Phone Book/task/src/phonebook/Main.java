package phonebook;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File directoryFile = new File("C:\\study\\jetbrains_academy\\java\\medium\\Phone Book\\directory.txt");
        File findFile = new File("C:\\study\\jetbrains_academy\\java\\medium\\Phone Book\\find.txt");
        String[] directoryArray = DataReader.readStringsFromFile(directoryFile);
        String[] findArray = DataReader.readStringsFromFile(findFile);
        PhoneBook.linearSearch(directoryArray, findArray);
        PhoneBook.jumpBubbleSearch(directoryArray, findArray);
        PhoneBook.quickSortBinarySearch(directoryArray, findArray);
        PhoneBook.hashTableSearch(directoryArray, findArray);
    }
}

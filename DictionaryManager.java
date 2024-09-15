package test;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    private static final DictionaryManager INSTANCE = new DictionaryManager();
    private Map<String, Dictionary> bookDictionaries;

    private DictionaryManager() {
        bookDictionaries = new HashMap<>();
    }

    public static DictionaryManager get() {
        return INSTANCE;
    }

//    public void addDictionary(String bookName, Dictionary dictionary) {
//        bookDictionaries.put(bookName, dictionary);
//    }

    public boolean query(String ...fileNames) {
        boolean found = false;
        String word = fileNames[fileNames.length - 1];

        for (int i = 0; i < fileNames.length - 1; i++) {
            String fileName  = fileNames[i];

            if(!this.bookDictionaries.containsKey(fileName)) {
                this.bookDictionaries.put(fileName, new Dictionary(fileName));
            }

            if(this.bookDictionaries.get(fileName).query(word)) {
                found = true;
            }
        }

        return found;
    }

    public boolean challenge(String ...fileNames) {
        boolean found = false;
        String word = fileNames[fileNames.length - 1];

        for (int i = 0; i < fileNames.length - 1; i++) {
            String fileName  = fileNames[i];

            if(!this.bookDictionaries.containsKey(fileName)) {
                this.bookDictionaries.put(fileName, new Dictionary(fileName));
            }

            if(this.bookDictionaries.get(fileName).challenge(word)) {
                found = true;
            }
        }

        return found;
    }
    public int getSize() {
        return bookDictionaries.size();
    }
}

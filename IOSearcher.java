package test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {
    public static boolean search(String word, String... fileNames) throws IOException {
        for (String fileName : fileNames) {
            boolean found = searchInFile(word, fileName);
            if (found) {
                return true;
            }
        }
        return false;
    }

    private static boolean searchInFile(String word, String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(word)) {
                    return true;
                }
            }
        }
        return false;
    }
}

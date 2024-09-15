package test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    private CacheManager existingWordsCache;
    private CacheManager nonExistingWordsCache;
    private BloomFilter bloomFilter;
    private String[] fileNames;

    private List<String> readFileWords(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineWords = line.split("\\s+"); // Split by whitespace
                for (String word : lineWords) {
                    words.add(word.toLowerCase()); // Convert to lowercase if needed
                }
            }
        }
        return words;
    }

    public Dictionary(String... fileNames) {
        existingWordsCache = new CacheManager(400, new LRU());
        nonExistingWordsCache = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, new String[]{"MD5", "SHA-1"});
        this.fileNames = fileNames.clone();
        for (String fileName : fileNames) {
            try {
                List<String> words = readFileWords(fileName);
                for (String word : words) {
                    bloomFilter.add(word);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean query(String word) {
        if (existingWordsCache.query(word)) {
            return true;
        } else if (nonExistingWordsCache.query(word)) {
            return false;
        } else {
            Set<String> wordSet = new HashSet<>();
            wordSet.add(word); // Create a set with the word
            boolean exists = bloomFilter.contains(word);
            if (exists) {
                existingWordsCache.put(word, wordSet);
            } else {
                nonExistingWordsCache.put(word, wordSet);
            }
            return exists;
        }
    }

    public boolean challenge(String word) {
        try {
            boolean result = IOSearcher.search(word,fileNames); // Assuming IOSearcher has static method search
            if(result)
                existingWordsCache.add(word);
            else
                nonExistingWordsCache.add(word);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

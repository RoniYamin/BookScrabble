package test;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CacheManager {
    private CacheReplacementPolicy crp;
    private int maxSize;
    private Map<String, Set<String>> cache;

    public CacheManager(int maxSize, CacheReplacementPolicy crp) {
        this.maxSize = maxSize;
        this.crp = crp;
        this.cache = new HashMap<>();
    }

    public boolean query(String word) {
        return cache.containsKey(word);
    }

    public void add(String word) {
        crp.add(word);
        cache.put(word, new HashSet<>()); // Add an empty set for the word
        if (cache.size() > maxSize) {
            String removedWord = crp.remove();
            cache.remove(removedWord);
        }
    }

    public Set<String> get(String word) {
        return cache.getOrDefault(word, new HashSet<>());
    }

    public void put(String word, Set<String> values) {
        crp.add(word);
        cache.put(word, values);
        if (cache.size() > maxSize) {
            String removedWord = crp.remove();
            cache.remove(removedWord);
        }
    }
}

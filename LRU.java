package test;


import java.util.LinkedHashMap;
import java.util.Map;

public class LRU implements CacheReplacementPolicy {
    private LinkedHashMap<String, Integer> cache;
    private int maxSize;

    // Constructor with maxSize argument
    public LRU(int maxSize) {
        this.maxSize = maxSize;
        // Using LinkedHashMap to maintain insertion order
        this.cache = new LinkedHashMap<String, Integer>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                // Remove the eldest entry when cache size exceeds maxSize
                return size() > maxSize;
            }
        };
    }

    // No-argument constructor
    public LRU() {
        // Default maxSize value
        this(100); // or any other default value you want
    }

    @Override
    public void add(String word) {
        cache.put(word, 0);
    }

    @Override
    public String remove() {
        // Get and remove the least recently used word from the cache
        return cache.keySet().iterator().next();
    }
}

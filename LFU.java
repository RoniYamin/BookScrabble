package test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.AbstractMap;

public class LFU implements CacheReplacementPolicy {
    private LinkedHashMap<String, Integer> cache;
    private PriorityQueue<Map.Entry<String, Integer>> frequencyQueue;

    public LFU() {
        this.cache = new LinkedHashMap<>(16, 0.75f, true);
        this.frequencyQueue = new PriorityQueue<>((a, b) -> {
            int freqCompare = a.getValue() - b.getValue();
            // If frequencies are equal, compare based on insertion order
            return freqCompare == 0 ? Integer.compare(cache.get(a.getKey()), cache.get(b.getKey())) : freqCompare;
        });
    }

    @Override
    public void add(String word) {
        cache.put(word, cache.getOrDefault(word, 0) + 1);
        updateFrequencyQueue(word);
    }

    @Override
    public String remove() {
        // Get and remove the least frequently used word from the cache
        String leastFrequent = frequencyQueue.poll().getKey();
        if (leastFrequent != null) {
            cache.remove(leastFrequent);
        }
        return leastFrequent;
    }

    private void updateFrequencyQueue(String word) {
        for (Map.Entry<String, Integer> entry : frequencyQueue) {
            if (entry.getKey().equals(word)) {
                frequencyQueue.remove(entry);
                frequencyQueue.offer(entry);
                return;
            }
        }
        frequencyQueue.offer(new AbstractMap.SimpleEntry<>(word, cache.get(word)));
    }
}

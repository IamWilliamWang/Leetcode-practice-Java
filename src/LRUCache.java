import java.util.*;
import java.util.stream.Collectors;

class LRUCacheBlock {
    private int key; // map中的键
    private int value; // map中的值

    public LRUCacheBlock(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

public class LRUCache {
    private LinkedList<LRUCacheBlock> cacheList = new LinkedList<>();
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        List<LRUCacheBlock> foundList = cacheList.stream().filter((element) -> element.getKey() == key).collect(Collectors.toList());
        if (foundList.isEmpty())
            return -1;
        LRUCacheBlock foundLRUCache = foundList.get(0);
        cacheList.remove(foundLRUCache);
        cacheList.add(foundLRUCache);
        return foundLRUCache.getValue();
    }

    public void put(int key, int value) {
        if (this.capacity <= 0)
            return;
        List<LRUCacheBlock> foundList = cacheList.stream().filter((element) -> element.getKey() == key).collect(Collectors.toList());
        if (!foundList.isEmpty()) {
            LRUCacheBlock foundLRUCache = foundList.get(0);
            foundLRUCache.setValue(value);
            cacheList.remove(foundLRUCache);
            cacheList.add(foundLRUCache);
        } else {
            if (cacheList.size() >= this.capacity)
                cacheList.remove(0);
            LRUCacheBlock newLRUCache = new LRUCacheBlock(key, value);
            cacheList.add(newLRUCache);
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2 /* capacity (缓存容量) */);

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));       // 返回 1
        cache.put(3, 3);    // 去除 key 2
        System.out.println(cache.get(2));       // 返回 -1 (未找到key 2)
        System.out.println(cache.get(3));       // 返回 3
        cache.put(4, 4);    // 去除 key 1
        System.out.println(cache.get(1));       // 返回 -1 (未找到 key 1)
        System.out.println(cache.get(3));       // 返回 3
        System.out.println(cache.get(4));       // 返回 4
    }
}

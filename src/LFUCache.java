import java.util.*;
import java.util.stream.Collectors;

class Cache {
    private int key; // map中的键
    private int value; // map中的值
    private int visitFreq = 1; // 访问频率
    private int clock; // 最后一次使用的时间

    public Cache(int key, int value) {
        this.key = key;
        this.value = value;
        this.clock = LFUCache.clockTime();
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

    public int getVisitFreq() {
        return visitFreq;
    }

    public void setVisitFreq(int visitFreq) {
        this.visitFreq = visitFreq;
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }
}

public class LFUCache {
    private ArrayList<Cache> cacheList = new ArrayList<>();
    private int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    private void sortWithLFU() {
        this.cacheList.sort(new Comparator<Cache>() {
            @Override
            public int compare(Cache o1, Cache o2) {
                return o1.getVisitFreq() == o2.getVisitFreq() ? o1.getClock() - o2.getClock() : o1.getVisitFreq() - o2.getVisitFreq();
            }
        });
    }

    public int get(int key) {
        List<Cache> foundList = cacheList.stream().filter((element) -> element.getKey() == key).collect(Collectors.toList());
        if (foundList.isEmpty())
            return -1;
        Cache foundCache = foundList.get(0);
        foundCache.setVisitFreq(foundCache.getVisitFreq() + 1);
        foundCache.setClock(clockTime());
        sortWithLFU();
        return foundCache.getValue();
    }

    public void put(int key, int value) {
        if (this.capacity <= 0)
            return;
        List<Cache> foundList = cacheList.stream().filter((element) -> element.getKey() == key).collect(Collectors.toList());
        if (!foundList.isEmpty()) {
            Cache foundCache = foundList.get(0);
            foundCache.setValue(value);
            foundCache.setVisitFreq(foundCache.getVisitFreq() + 1);
            foundCache.setClock(clockTime());
        } else {
            if (cacheList.size() >= this.capacity)
                cacheList.remove(0);
            Cache newCache = new Cache(key, value);
            cacheList.add(newCache);
        }
        sortWithLFU();
    }

    /* 总时钟模块 */
    private static int globalClock = 0;

    static int clockTime() {
        return globalClock++;
    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2 /* capacity (缓存容量) */);

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

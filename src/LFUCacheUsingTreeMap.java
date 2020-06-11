import java.util.*;

class CacheBlock implements Comparable<CacheBlock> {
    private int key; // map中的键
    private int value; // map中的值
    private int visitFreq = 1; // 访问频率
    private int clock; // 最后一次使用的时间

    public CacheBlock(int key, int value) {
        this.key = key;
        this.value = value;
        this.clock = LFUCacheUsingTreeMap.clockTime();
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

    @Override
    public int compareTo(CacheBlock o) {
        CacheBlock cache1 = this;
        CacheBlock cache2 = o;
        if (cache1.visitFreq == cache2.visitFreq)
            return cache1.clock - cache2.clock;
        return cache1.visitFreq - cache2.visitFreq;
    }
}

public class LFUCacheUsingTreeMap {
    private int capacity;
    private HashMap<Integer, CacheBlock> cacheHashMap; // 保存所有的CacheBlocks
    private TreeMap<Integer, CacheBlock> cacheTreeMap; // 经过LFU排序后的CacheBlocks

    public LFUCacheUsingTreeMap(int capacity) {
        // 初始化各变量
        this.capacity = capacity;
        cacheHashMap = new HashMap<>();
        cacheTreeMap = new TreeMap<>((key1, key2) -> {
            if (cacheHashMap.get(key1) == null) // key1是从未出现过的key，一直返回-1。就让它一直向左下角移动直到结尾返回null
                return -1;
            return cacheHashMap.get(key1).compareTo(cacheHashMap.get(key2));
        });
    }

    private void updateTree(CacheBlock cache) {
        cacheTreeMap.remove(cache.getKey());
        cacheTreeMap.put(cache.getKey(), cache);
    }

    public int get(int key) {
        CacheBlock foundCacheBlock = cacheTreeMap.get(key);
        if (foundCacheBlock == null)
            return -1;
        foundCacheBlock.setVisitFreq(foundCacheBlock.getVisitFreq() + 1);
        foundCacheBlock.setClock(clockTime());
        updateTree(foundCacheBlock);
        return foundCacheBlock.getValue();
    }

    public void put(int key, int value) {
        if (this.capacity <= 0)
            return;
        CacheBlock foundCacheBlock = cacheTreeMap.get(key);
        // 如果找到了，就修改value和访问频率与时钟
        if (foundCacheBlock != null) {
            foundCacheBlock.setValue(value);
            foundCacheBlock.setVisitFreq(foundCacheBlock.getVisitFreq() + 1);
            foundCacheBlock.setClock(clockTime());
            updateTree(foundCacheBlock);
        } else {
            // 没找到，就新建一个
            if (cacheTreeMap.keySet().size() >= this.capacity)
                cacheTreeMap.remove(cacheTreeMap.firstKey());
            CacheBlock newCacheBlock = new CacheBlock(key, value);
            cacheHashMap.put(key, newCacheBlock); // 先存入HashMap
            cacheTreeMap.put(key, newCacheBlock); // 后存入TreeMap
        }
    }

    /* 总时钟模块 */
    private static int globalClock = 0;

    static int clockTime() {
        return globalClock++;
    }

    public static void main(String[] args) {
        LFUCacheUsingTreeMap cache = new LFUCacheUsingTreeMap(2 /* capacity (缓存容量) */);

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

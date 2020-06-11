package Util;

import java.util.*;
import java.util.stream.Collectors;

public class Counter {
    /**
     * 保存各个元素和其个数
     */
    public class Entry implements Map.Entry<Object, Integer> {
        private Object key; // 保存数据
        private Integer value; // 保存个数

        public Entry(Object key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public Object getKey() {
            return key;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public Integer getValue() {
            return value;
        }

        public Integer setValue(Integer value) {
            var oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            if (key instanceof Character)
                return "'" + key + "'" + ": " + value;
            else if (key instanceof String)
                return "\"" + key + "\"" + ": " + value;
            return key + ": " + value;
        }
    }

    private TreeSet<Entry> tree;

    /**
     * int数组的Counter
     * @param numbers
     */
    public Counter(int[] numbers) {
        if (numbers == null || numbers.length < 1)
            return;
        var maxKey = Arrays.stream(numbers).max().getAsInt();
        tree = new TreeSet<>(Comparator.comparingInt(entry -> (int) entry.key - entry.value * maxKey));
        for (var number : numbers) {
            var foundEntry = tree.parallelStream().filter(obj -> obj.getKey().equals(number)).collect(Collectors.toList());
            if (foundEntry.size() != 0) {
                var entry = foundEntry.get(0);
                tree.remove(entry);
                entry.setValue(entry.getValue() + 1);
                tree.add(entry);
                continue;
            }
            tree.add(new Entry(number, 1));
        }
    }

    /**
     * double数组的Counter
     * @param numbers
     */
    public Counter(double[] numbers) {
        if (numbers == null || numbers.length < 1)
            return;
        var maxKey = Arrays.stream(numbers).max().getAsDouble();
        tree = new TreeSet<>(Comparator.comparingDouble(entry -> (double) entry.key - entry.value * maxKey));
        for (var number : numbers) {
            var foundEntry = tree.parallelStream().filter(obj -> obj.getKey().equals(number)).collect(Collectors.toList());
            if (foundEntry.size() != 0) {
                var entry = foundEntry.get(0);
                tree.remove(entry);
                entry.setValue(entry.getValue() + 1);
                tree.add(entry);
                continue;
            }
            tree.add(new Entry(number, 1));
        }
    }

    /**
     * char数组的Counter
     * @param chars
     */
    public Counter(char[] chars) {
        if (chars == null || chars.length < 1)
            return;
        List<Character> charsList = new ArrayList<>();
        for (var ch : chars)
            charsList.add(ch);
        var maxKey = charsList.stream().mapToInt((ch) -> (int) (ch)).max().getAsInt();
        tree = new TreeSet<>(Comparator.comparingDouble(entry -> (char) entry.key - entry.value * maxKey));
        for (var ch : chars) {
            var foundEntry = tree.parallelStream().filter(obj -> obj.getKey().equals(ch)).collect(Collectors.toList());
            if (foundEntry.size() != 0) {
                var entry = foundEntry.get(0);
                tree.remove(entry);
                entry.setValue(entry.getValue() + 1);
                tree.add(entry);
                continue;
            }
            tree.add(new Entry(ch, 1));
        }
    }

    /**
     * String.toCharArray()的Counter
     * @param string
     */
    public Counter(String string) {
        this(string.toCharArray());
    }

    /**
     * String数组的Counter
     * @param strings
     */
    public Counter(String[] strings) {
        if (strings == null || strings.length < 1)
            return;
        var maxKey = Arrays.stream(strings).mapToInt(String::hashCode).max().getAsInt();
        var str2Index = new HashMap<String, Integer>();
        for (int i = 0; i < strings.length; i++) // 每个String分配一个id，就和int数组的处理一样了
            str2Index.put(strings[i], i);

        tree = new TreeSet<>(Comparator.comparingDouble(entry -> str2Index.get((String) entry.key) - entry.value * strings.length));
        for (var string : strings) {
            var foundEntry = tree.parallelStream().filter(obj -> obj.getKey().equals(string)).collect(Collectors.toList());
            if (foundEntry.size() != 0) {
                var entry = foundEntry.get(0);
                tree.remove(entry);
                entry.setValue(entry.getValue() + 1);
                tree.add(entry);
                continue;
            }
            tree.add(new Entry(string, 1));
        }
    }

    /**
     * 获得Counter的所有元素和出现次数
     * @return
     */
    public ArrayList<Entry> getMostCommon() {
        return this.getMostCommon(tree.size());
    }

    /**
     * 获得Counter中出现最多的n个元素和出现次数
     * @param n
     * @return
     */
    public ArrayList<Entry> getMostCommon(int n) {
        ArrayList<Entry> result = new ArrayList<>();
        for (var entry : tree) {
            result.add(entry);
            if (result.size() >= n)
                break;
        }
        return result;
    }

    @Override
    public String toString() {
        return this.tree.toString();
    }
}

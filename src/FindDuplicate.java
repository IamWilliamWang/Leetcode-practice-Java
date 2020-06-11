import Util.Tuple;
import Util.Counter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Deprecated
class DeprecatedCounter {
    private ArrayList<Integer> elements = new ArrayList<>();
    private ArrayList<Integer> times = new ArrayList<>();
    private HashMap<Integer, Integer> counter = new HashMap<>();

    public DeprecatedCounter(Integer[] elements) {
        for (var element : elements)
            counter.put(element, counter.getOrDefault(element, 0) + 1);
        for (var entry : counter.entrySet()) {
            this.elements.add(entry.getKey());
            this.times.add(entry.getValue());
        }
    }

    public HashMap<Integer, Integer> getCounter() {
        return counter;
    }

    public ArrayList<Tuple<Integer>> getMostCommon() {
        return this.getMostCommon(elements.size());
    }

    public ArrayList<Tuple<Integer>> getMostCommon(int n) {
        var tempTimes = (ArrayList<Integer>) times.clone();
        ArrayList<Tuple<Integer>> result = new ArrayList<>();
        while (!tempTimes.isEmpty() && result.size() < n) {
            var maxTime = tempTimes.stream().mapToInt(Integer::intValue).max().getAsInt();
            var maxIndex = tempTimes.indexOf(maxTime);
            result.add(new Tuple<>(this.elements.get(maxIndex), this.times.get(maxIndex)));

        }
        return result;
    }
}

public class FindDuplicate {
    public int findDuplicate(int[] nums) {
        var counter = new Counter(nums).getMostCommon(1);
        return (int)counter.get(0).getKey();
    }

    public int findDuplicate_fast(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (var num : nums) {
            if (set.contains(num))
                return num;
            else
                set.add(num);
        }
        return -1;
    }

    public static void main(String[] args) {
        var numbers = IntStream.range(0, 10000);
        var numberList = numbers.boxed().collect(Collectors.toList());
        numberList.add(5000);
        System.out.println(new FindDuplicate().findDuplicate(numberList.stream().mapToInt(Integer::intValue).toArray()));
        System.out.println(new FindDuplicate().findDuplicate_fast(numberList.stream().mapToInt(Integer::intValue).toArray()));
    }
}

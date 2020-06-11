import Util.Tuple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.IntStream;

import static Util.test_script.speedtest;

public class SubarraysDivByK {
    private <T extends Integer> int min(Collection<T> collection) {
        var optionalMin = collection.stream().mapToInt(Integer::intValue).min();
        return optionalMin.isPresent() ? optionalMin.getAsInt() : -1;
    }

    private <T extends Integer> int max(Collection<T> collection) {
        var optionalMax = collection.stream().mapToInt(Integer::intValue).max();
        return optionalMax.isPresent() ? optionalMax.getAsInt() : -1;
    }

    public int subarraysDivByK_twoLoop(int[] A, int K) {
        if (A == null || A.length == 0)
            return 0;
        var sumDict = new HashMap<Integer, Integer>();
        sumDict.put(0, 1);
        var sumNum = 0;
        var resultCount = 0;
        for (var num : A) {
            sumNum += num;
            for (int potentialSum = sumNum; potentialSum > min(sumDict.keySet()) - 1; potentialSum -= K) {
                resultCount += sumDict.getOrDefault(potentialSum, 0);
            }
            for (int potentialSum = sumNum + K; potentialSum < max(sumDict.keySet()) + 1; potentialSum += K) {
                resultCount += sumDict.getOrDefault(potentialSum, 0);
            }
            sumDict.put(sumNum, sumDict.getOrDefault(sumNum, 0) + 1);
        }
        return resultCount;
    }

    public int subarraysDivByK(int[] A, int K) {
        if (A == null || A.length == 0)
            return 0;
        var sumDict = new HashMap<Integer, Integer>();
        sumDict.put(0, 1);
        var sumNum = 0;
        var resultCount = 0;
        for (var num : A) {
            sumNum += num;
            var key = sumNum % K;
            if (key < 0) key += K;
            resultCount += sumDict.getOrDefault(key, 0);
            sumDict.put(key, sumDict.getOrDefault(key, 0) + 1);
        }
        return resultCount;
    }

    public static void main(String[] args) {
//        System.out.println(new SubarraysDivByK().subarraysDivByK_twoLoop(new int[]{4, 5, 0, -2, -3, 1}, 5));
//        System.out.println(new SubarraysDivByK().subarraysDivByK(new int[]{4, 5, 0, -2, -3, 1}, 5));
        /* 多个参数进行speedtest的例子 */
        var functions = new ArrayList<Function<Tuple, Integer>>(); // 保存要测试的函数
        functions.add(tuple -> new SubarraysDivByK().subarraysDivByK_twoLoop((int[]) tuple.getFirst(), (int) tuple.getSecond()));
        functions.add(tuple -> new SubarraysDivByK().subarraysDivByK((int[]) tuple.getFirst(), (int) tuple.getSecond()));
        var names = new Tuple<String>("subarraysDivByK_twoLoop", "subarraysDivByK"); // 保存要测试的函数names
        var argument = new Tuple<Object>(new int[]{4, 5, 0, -2, -3, 1}, 5); // 由于参数的类型不一样，只能存成Object
        speedtest(functions, names, argument); // 进行测试
    }
}

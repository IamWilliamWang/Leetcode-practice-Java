import Util.Tuple;

import static Util.test_script.speedtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProductExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        if (nums == null)
            return new int[]{};
        var zeroCount = Arrays.stream(nums).filter(num -> num == 0).count();
        if (zeroCount > 1)
            return new int[nums.length];
        else if (zeroCount == 1) {
            var result = new int[nums.length];
            var numsList = Arrays.stream(nums).boxed().collect(Collectors.toList());
            numsList.add(0, 1); // 防止numsList只有一个元素的情况reduce会报错
            result[numsList.indexOf(0) - 1] = numsList.stream().reduce((x, y) -> x * y).get();
            return result;
        }
        var multipliesList = new ArrayList<Integer>();
        var multiplies = 1;
        for (var num : nums) {
            multiplies *= num;
            multipliesList.add(multiplies);
        }
        var result = new ArrayList<Integer>();
        for (var i : IntStream.range(0, nums.length).toArray())
            result.add((i > 0 ? multipliesList.get(i - 1) : 1) * (multipliesList.get(multipliesList.size() - 1) / multipliesList.get(i)));
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] productExceptSelf_withoutMultiply(int[] nums) {
        if (nums == null)
            return new int[]{};
        var multiSum = 1;
        var leftMultipliesList = new ArrayList<Integer>();
        var rightMultipliesList = new ArrayList<Integer>();
        for (var num : nums) {
            multiSum *= num;
            leftMultipliesList.add(multiSum);
        }
        multiSum = 1;
        for (var i = nums.length - 1; i > -1; i--) {
            var num = nums[i];
            multiSum *= num;
            rightMultipliesList.add(0, multiSum);
        }
        var result = new ArrayList<Integer>();
        for (var i : IntStream.range(0, nums.length).toArray())
            result.add((i > 0 ? leftMultipliesList.get(i - 1) : 1) * (i < nums.length - 1 ? rightMultipliesList.get(i + 1) : 1));
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        var sol = new ProductExceptSelf();
        var functions = new Tuple<Function<Object, List<Integer>>>((argument) -> Arrays.stream(sol.productExceptSelf((int[]) argument)).boxed().collect(Collectors.toList())
                , (argument) -> Arrays.stream(sol.productExceptSelf_withoutMultiply((int[]) argument)).boxed().collect(Collectors.toList()));
        var funcNames = new Tuple<>("productExceptSelf", "productExceptSelf_withoutMultiply");
        var argument = new int[]{0};
        speedtest(functions, funcNames, argument);
        argument = new int[]{0, 0};
        speedtest(functions, funcNames, argument);
        argument = new int[]{1, 2, 3, 4};
        speedtest(functions, funcNames, argument);
        argument = IntStream.range(0, 10000).toArray();
        speedtest(functions, funcNames, argument);
    }
}

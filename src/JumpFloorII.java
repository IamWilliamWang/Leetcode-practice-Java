import Util.Tuple;

import static Util.test_script.speedtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

public class JumpFloorII {
    public int jumpFloorIIRecursiveSlow(int number) { // 递归
        if (number == 1) // 如果n==1，终止递归
            return 1;
        int sum = 0; // 求和
        for (int i = 1; i < number; i++) // 上一步有可能在任何一个台阶
            sum += jumpFloorIIRecursiveSlow(number - i); // 从台阶1到台阶n-1跨到当前台阶
        sum++; // 一步跨到当前台阶
        return sum;
    }

    private HashMap<Integer, Integer> cache = new HashMap<>();

    public int jumpFloorIIRecursive(int number) { // 递归(优化)
        if (cache.containsKey(number)) // 调用缓存
            return cache.get(number);
        if (number == 1) // 如果n==1，终止递归
            return 1;
        int sum = 0; // 求和
        for (int i = 1; i < number; i++) // 上一步有可能在任何一个台阶
            sum += jumpFloorIIRecursive(number - i); // 从台阶1到台阶n-1跨到当前台阶
        sum++; // 一步跨到当前台阶
        cache.put(number, sum);
        return sum;
    }

    public int jumpFloorIISlow(int number) { // 迭代
        int[] dp = new int[number]; // 使用数组代替递归，减少了一个number算了好几遍的问题
        dp[0] = 1;
        for (int i = 1; i < number; i++)
            dp[i] = Arrays.stream(dp).sum() + 1; // 把数组前面的全求和，再加1，和递归的思路一样
        return dp[number - 1]; // 返回第number个台阶对应的总和
    }

    public int jumpFloorII(int number) { // 迭代(优化)
        int[] dp = new int[number]; // 使用数组代替递归，减少了一个number算了好几遍的问题
        dp[0] = 1;
        int sum = 0;
        for (int i = 1; i < number; i++) { // 把数组前面的全求和，再加1，和递归的思路一样
            sum += dp[i - 1];
            dp[i] = sum + 1;
        }
        return dp[number - 1]; // 返回第number个台阶对应的总和
    }

    public static void main(String[] args) {
        var functions = new Tuple<Function<Integer, Integer>>(new JumpFloorII()::jumpFloorII, new JumpFloorII()::jumpFloorIISlow, new JumpFloorII()::jumpFloorIIRecursive/*, sol::jumpFloorIIRecursiveSlow*/);
//        var funcNames = new Tuple<>("递归", "递归(优化)", "迭代", "迭代(优化)");
        var funcNames = new Tuple<>("jumpFloorII", "jumpFloorIISlow", "jumpFloorIIRecursive"/*, "jumpFloorIIRecursiveSlow"*/);
        Integer argument = 20;
        speedtest(functions, funcNames, argument);
    }
}

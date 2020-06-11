package Util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

public class test_script {
    private static int speedtestCounter = 1;

    private static <ArgType, RtType> RtType invoke(Function<ArgType, RtType> function, ArgType argument) {
        /// java.lang.reflect.Method 和 java.util.function.Function学习：
        /// https://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string
        /// https://www.cnblogs.com/chengmi/p/10900218.html
        return function.apply(argument);
    }

    /**
     * 对相似函数（参数类型都相同）统一进行速度测试，并输出结果
     *
     * @param functions 包含所有函数的tuple或者list
     * @param funNames  所有函数的函数名的tuple或者list
     * @param argument  包含所有调用参数的tuple或者list
     * @param <ArgType> 传入的唯一参数的类型
     * @param <RtType>  返回的参数类型
     */
    public static <ArgType, RtType> void speedtest_format(List<Function<ArgType, RtType>> functions, List<String> funNames, ArgType argument) {
        if (functions.isEmpty())
            return;
        System.out.printf("----- speedtest round %d -----\n", test_script.speedtestCounter++);
        var results = new ArrayList<RtType>();
        var runtimes = new ArrayList<Double>();
        int[] columnsize = new int[]{8, 6, 4};
        //开始调用
        for (var i : IntStream.range(0, functions.size()).toArray()) {
            var function = functions.get(i);
            //进行调用，记录结果和运行时间
            var start = System.currentTimeMillis();
            var result = invoke(function, argument);
            var runtime = 1e-3 * (System.currentTimeMillis() - start);
            results.add(result);
            runtimes.add(runtime);
            //调整每一列的宽度，不要让某一列的输出越界
            columnsize[0] = Math.max(columnsize[0], funNames.get(i).length());
            columnsize[1] = Math.max(columnsize[1], result.toString().length());
            columnsize[2] = Math.max(columnsize[2], Double.valueOf(runtime).toString().length());
        }
        //开始输出表格
        var c_style_format = "%-{$1}s|%-{$2}s|%-{$3}s".replace("{$1}", columnsize[0] + "").replace("{$2}", columnsize[1] + "").replace("{$3}", columnsize[2] + "");
        System.out.printf(c_style_format + "\n", "Function", "Return", "Time");
        for (var i : IntStream.range(0, results.size()).toArray())
            System.out.printf(c_style_format + " s\n", funNames.get(i), results.get(i), runtimes.get(i));
        //输出一致性
        var equal = true;
        for (int i = 1; i < results.size(); i++) {
            if (results.get(i) instanceof List) {//直接调用sort会报错，只能使用List的通用方法进行比较
                if (((List) results.get(i)).containsAll((List) results.get(0)) &&
                        ((List) results.get(0)).containsAll((List) results.get(i)))
                    continue;
            }
            if (!results.get(i - 1).equals(results.get(i))) {
                equal = false;
                break;
            }
        }
        System.out.println("All equals: " + equal);
        //输出执行时间比
        var compareTimesStr = "Execution time ratio: ";
        var runtimesMin = runtimes.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
        if (runtimesMin < 1e-6)
            return;
        for (var runtime : runtimes)
            compareTimesStr += (runtime != runtimesMin ? runtime / runtimesMin : "1") + ": ";
        System.out.println(compareTimesStr.substring(0, compareTimesStr.length() - 2));
    }

    /**
     * 对相似函数（每个函数的参数都相同）统一进行速度测试，并实时的输出结果（只支持一个参数，如果有多个参数请使用Tuple封装）
     *
     * @param functions 包含所有函数的tuple或者list
     * @param funNames  所有函数的函数名的tuple或者list
     * @param argument  包含所有调用参数的tuple或者list
     * @param <ArgType> 传入的唯一参数的类型
     * @param <RtType>  返回的参数类型
     */
    public static <ArgType, RtType> void speedtest_realtime(List<Function<ArgType, RtType>> functions, List<String> funNames, ArgType argument) {
        if (functions.isEmpty())
            return;
        System.out.printf("----- speedtest round %d -----\n", test_script.speedtestCounter++);
        var results = new ArrayList<RtType>();
        var runtimes = new ArrayList<Double>();
        int[] columnsize = new int[]{8, 6, 4};
        for (var funName : funNames)
            columnsize[0] = Math.max(columnsize[0], funName.length());
        //开始输出表格
        var c_style_format = "%-{$1}s|%-{$2}s|%-{$3}s".replace("{$1}", columnsize[0] + "").replace("{$2}", columnsize[1] + "").replace("{$3}", columnsize[2] + "");
        System.out.printf(c_style_format + "\n", "Function", "Return", "Time");
        //开始调用
        for (var i : IntStream.range(0, functions.size()).toArray()) {
            var function = functions.get(i);
            System.out.printf(c_style_format.split("\\|")[0] + "|", funNames.get(i));
            //进行调用，记录结果和运行时间
            var start = System.currentTimeMillis();
            var result = invoke(function, argument);
            var runtime = 1e-3 * (System.currentTimeMillis() - start);
            results.add(result);
            runtimes.add(runtime);
            if (result.toString().length() > columnsize[1]) {
                columnsize[1] = result.toString().length();
                c_style_format = "%-{$1}s|%-{$2}s|%-{$3}s".replace("{$1}", columnsize[0] + "").replace("{$2}", columnsize[1] + "").replace("{$3}", columnsize[2] + "");
            }
            System.out.printf(c_style_format.substring(c_style_format.indexOf('|') + 1) + " s\n", result, runtime);
        }
        //输出一致性
        var equal = true;
        for (int i = 1; i < results.size(); i++) {
            if (results.get(i) instanceof List) {//直接调用sort会报错，只能使用List的通用方法进行比较
                if (((List) results.get(i)).containsAll((List) results.get(0)) &&
                        ((List) results.get(0)).containsAll((List) results.get(i)))
                    continue;
            }
            if (!results.get(i - 1).equals(results.get(i))) {
                equal = false;
                break;
            }
        }
        System.out.println("All equals: " + equal);
        //输出执行时间比
        var compareTimesStr = "Execution time ratio: ";
        var runtimesMin = runtimes.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
        if (runtimesMin < 1e-6)
            return;
        for (var runtime : runtimes)
            compareTimesStr += (runtime != runtimesMin ? runtime / runtimesMin : "1") + ": ";
        System.out.println(compareTimesStr.substring(0, compareTimesStr.length() - 2));
    }

    /**
     * @see #speedtest_format(List, List, Object)
     */
    public static <ArgType, RtType> void speedtest(List<Function<ArgType, RtType>> functions, List<String> funNames, ArgType argument) {
        speedtest_format(functions, funNames, argument);
    }

    public static int binarySearch(List<Integer> nums, int target) {
        return binarySearch(nums, target, 0, null);
    }

    /**
     * 二分查找法，返回target出现的位置，找不到返回-1。也可以搜索左边界(首次出现的位置)和右边界(最后出现的位置)，找不到返回-1
     *
     * @param nums   整形数组
     * @param target 要查找的数字
     * @param mode   0为二分查找，1为左边界搜索，2为右边界搜索
     * @param key    搜索时用什么值进行比较
     * @return
     */
    public static int binarySearch(List<Integer> nums, Integer target, int mode, Function<Integer, Integer> key) {
        if (key == null)
            key = x -> x;
        var left = 0;
        var right = nums.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (key.apply(nums.get(mid)) < target)
                left = mid + 1;
            else if (key.apply(nums.get(mid)) > target)
                right = mid - 1;
            else if (key.apply(nums.get(mid)).equals(target)) {
                if (mode == 1)
                    right = mid - 1;
                else if (mode == 2)
                    left = mid + 1;
                else
                    return mid;
            } else
                throw new IllegalArgumentException("The search was accidentally terminated. Check whether the \"key\" function returns the specified output");
        }
        if (mode == 1) {
            if (left >= nums.size() || !nums.get(left).equals(target))
                return -1;
            return left;
        } else if (mode == 2) {
            if (right < 0 || !nums.get(right).equals(target))
                return -1;
            return right;
        } else
            return -1;
    }
}

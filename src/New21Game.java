import Util.Tuple;

import static Util.test_script.speedtest;

import java.util.Arrays;
import java.util.function.Function;

public class New21Game {
    public double new21Game_slow(int N, int K, int W) {
        int limit爆点Inclusive = N, limit抽到点数为止Exclusive = K, max卡牌点数 = W;
        double[] array = new double[limit抽到点数为止Exclusive + max卡牌点数];
        Arrays.fill(array, limit抽到点数为止Exclusive, Math.min(limit爆点Inclusive + 1, array.length), 1);
        for (int i = limit抽到点数为止Exclusive - 1; i >= 0; i--) {
            var sum = 0.0;
            for (int j = i + 1; j < i + max卡牌点数 + 1; j++)
                sum += array[j];
            array[i] = sum / max卡牌点数;
        }
        return array[0];
    }

    public double new21Game(int N, int K, int W) {
        int limit爆点Inclusive = N, limit抽到点数为止Exclusive = K, max卡牌点数 = W;
        double[] array = new double[limit抽到点数为止Exclusive + max卡牌点数];
        Arrays.fill(array, limit抽到点数为止Exclusive, Math.min(limit爆点Inclusive + 1, array.length), 1);
        var sums = 0.0;
        for (int i = limit抽到点数为止Exclusive + 1; i < limit抽到点数为止Exclusive + max卡牌点数; i++)
            sums += array[i];
        for (int i = limit抽到点数为止Exclusive - 1; i >= 0; i--) {
            sums += array[i + 1];
            array[i] = sums / max卡牌点数;
            sums -= array[i + max卡牌点数];
        }
        return array[0];
    }

    public static void main(String[] args) {
        var sol = new New21Game();
        var functions = new Tuple<Function<Tuple<Integer>, Double>>((tuple) -> sol.new21Game_slow(tuple.getFirst(), tuple.getSecond(), tuple.getThird()),
                (tuple) -> sol.new21Game(tuple.getFirst(), tuple.getSecond(), tuple.getThird()));
        var funcNames = new Tuple<String>("new21Game_slow", "new21Game");
        speedtest(functions, funcNames, new Tuple<>(1, 0, 1));
        speedtest(functions, funcNames, new Tuple<>(10, 1, 10));
        speedtest(functions, funcNames, new Tuple<>(6, 1, 10));
        speedtest(functions, funcNames, new Tuple<>(21, 17, 10));
        speedtest(functions, funcNames, new Tuple<>(7467, 6303, 1576));
        speedtest(functions, funcNames, new Tuple<>(9811, 8776, 1096));
        speedtest(functions, funcNames, new Tuple<>(7467, 6303, 1576));
    }
}

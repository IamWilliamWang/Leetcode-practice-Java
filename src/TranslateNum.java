import Util.Tuple;

import java.util.function.Function;

import static Util.test_script.speedtest;

public class TranslateNum {
    private int count;
    private String numStr;

    private void dp(int i) {
        if (i >= numStr.length()) {
            count++;
            return;
        }
        dp(i + 1);
        if (i < numStr.length() - 1) {
            if (numStr.charAt(i) <= '1' || (numStr.charAt(i) == '2' && numStr.charAt(i + 1) <= '5'))
                if (numStr.charAt(i) != '0')
                    dp(i + 2);
        }
    }

    public int translateNum(int num) {
        count = 0;
        numStr = Integer.valueOf(num).toString();
        dp(0);
        return count;
    }

    public static void main(String[] args) {
        var sol = new TranslateNum();
        var functions = new Tuple<Function<Integer, Integer>>(sol::translateNum, (x) -> 2);
        var funcName = new Tuple<>("translateNum", "<lambda>");
        var argument = 12;
        speedtest(functions, funcName, argument);
        functions.set(1, (x) -> 1);
        argument = 26;
        speedtest(functions, funcName, argument);
        argument = 506;
        speedtest(functions, funcName, argument);
        argument = 5006;
        speedtest(functions, funcName, argument);
        functions.set(1, (x) -> 2);
        argument = 50106;
        speedtest(functions, funcName, argument);
        argument = 18580;
        speedtest(functions, funcName, argument);
        functions.set(1, (x) -> 4);
        argument = 185810;
        speedtest(functions, funcName, argument);
    }
}

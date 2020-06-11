import java.util.HashMap;

public class MaxRepetitions {
    private int getHcf(int x, int y) {
        int hcf = 1;
        int small = 0;
        if (x > y)
            small = y;
        else small = x;
        for (int i = 1; i <= small; i++) {
            if (x % i == 0 && y % i == 0)
                hcf = i;
        }
        return hcf;
    }

    private String star(String str, int times) {
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 2; i <= times; i++)
            stringBuilder.append(str);
        return stringBuilder.toString();
    }

    private HashMap<String, Integer> cache = new HashMap<>();
    private int getLongstrLoopLeastTimesToContainsShortstr(String longStr, String shortStr, int timesMaxLimit) {
        int index = -1;
        int ans = 1;
        if (cache.containsKey(shortStr)) {
            if (cache.get(shortStr) <= timesMaxLimit)
                return cache.get(shortStr);
            ans = cache.get(shortStr) - longStr.length();
            if (ans < 1) ans = 1;
        }

        for (char shortCh : shortStr.toCharArray()) {
            if (timesMaxLimit <= ans)
                break;
            int foundIndex = this.star(longStr, 2).indexOf(shortCh, index + 1);
            if (foundIndex == -1)
                return -1;
            if (foundIndex >= longStr.length()) {
                index = foundIndex - longStr.length();
                ans++;
            } else
                index = foundIndex;
        }
        cache.put(shortStr, ans);
        return ans;
    }

    int step = 0;
    private int step(boolean forward) {
        if (step == 0)
            step = 1;
        if (forward) {
            step *= 2;
            return step;
        } else {
            step /= 2;
            return -step;
        }
    }

    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        int hcf = this.getHcf(n1, n2);
        n1 /= hcf;
        n2 /= hcf;
        s2 = this.star(s2, n2);
        int M = 1;
        int jieStartAt = M, jieEndAt = Integer.MAX_VALUE;
        while (true) {
            if (jieEndAt - jieStartAt == 1)
                return jieStartAt;
            int least = this.getLongstrLoopLeastTimesToContainsShortstr(s1, star(s2, M), n1 + 1);
            if (least == -1)
                return 0;
            if (least <= n1) {
                System.out.println("Good M:" + M + ",step:" + step);
                jieStartAt = M;
                M += step(true);
                continue;
            } else {
                System.out.println("Bad M:" + M + ",step:" + step);
                jieEndAt = M;
                M += step(false);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new MaxRepetitions().getMaxRepetitions("lovenicoloveliveniconiconiconiniconjcoaaajo",
                201641,
                "lovenanjo",
                401));
    }
}
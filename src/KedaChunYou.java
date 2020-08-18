import java.util.Arrays;
import java.util.Scanner;
// 科大讯飞：春游（所有人塞车里，最少需要几辆）
public class KedaChunYou {
    static int[] peopleWeight;
    static int maxWeight;
    static int result = Integer.MAX_VALUE;

    private static void dp(int i, int nowWeight, int fullShips) {
        if (i >= peopleWeight.length) {
            result = Math.min(result, fullShips + 1);
            return;
        }

        int restWeight = maxWeight - nowWeight;
        if (peopleWeight[i] <= restWeight) {
            dp(i + 1, nowWeight + peopleWeight[i], fullShips);
        } else {
            dp(i + 1, peopleWeight[i], fullShips + 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int peopleCount = sc.nextInt();
        peopleWeight = new int[peopleCount];
        maxWeight = sc.nextInt();
        for (int i = 0; i < peopleCount; i++)
            peopleWeight[i] = sc.nextInt();
        Arrays.sort(peopleWeight);
        dp(0, 0, 0);
        System.out.println(result);
    }
}

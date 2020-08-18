// 科大讯飞：2维空间（线性规划）
public class Keda2DSpace {
    private static double average(int[] numbers) {
        int sum = 0;
        for (int num : numbers)
            sum += num;
        return 1.0 * sum / numbers.length;
    }

    public static void main(String[] args) {
        int[] X = new int[]{1, 2, 3, 4};
        int[] Y = new int[]{6, 5, 7, 10};
        int n = 4;
        double tmp = 0, tmp2 = 0;
        for (int i = 0; i < 4; i++) {
            tmp += X[i] * Y[i];
            tmp2 += X[i] * X[i];
        }
        tmp -= n * average(X) * average(Y);
        tmp2 -= n * average(X) * average(X);
        double a = tmp / tmp2;
        double b = average(Y) - a * average(X);
        System.out.println("b=" + a);
        System.out.println("a=" + b);
    }
}
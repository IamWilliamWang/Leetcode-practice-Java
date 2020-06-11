import Util.Tuple;

import java.util.ArrayList;

public class OfferFindNumberInMatrix {
    private int target;
    private int[][] array;
    private final int UNKNOWN = Integer.MIN_VALUE;

    private int getNum(int i, int j) {
        int rows = this.array.length;
        int cols = this.array[0].length;
        if (i < 0 || i >= rows || j < 0 || j >= cols)
            return UNKNOWN;
        return this.array[i][j];
    }

    private int closest2Target(ArrayList<Integer> values) {
        int minValue = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);
            if (value > this.target || value == UNKNOWN)
                continue;
            if (this.target - value < minValue) {
                minValue = this.target - value;
                minIndex = i;
            }
        }
        return minIndex;
    }

    private Tuple<Integer> nextStep(int now_x, int now_y) {
        ArrayList<Integer> neighbour = new ArrayList<>();
        neighbour.add(this.getNum(now_x + 1, now_y));
        neighbour.add(this.getNum(now_x, now_y + 1));
        neighbour.add(this.getNum(now_x - 1, now_y));
        neighbour.add(this.getNum(now_x, now_y - 1));
        int best_index = this.closest2Target(neighbour);
        switch (best_index) {
            case 0:
                return new Tuple<>(now_x + 1, now_y);
            case 1:
                return new Tuple<>(now_x, now_y + 1);
            case 2:
                return new Tuple<>(now_x - 1, now_y);
            case 3:
                return new Tuple<>(now_x, now_y - 1);
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
//        this.target = target;
//        this.array = matrix;
//        int pointer_x = 0, pointer_y = 0;
//        while (true) {
//            Tuple<Integer> ret = this.nextStep(pointer_x, pointer_y);
//            pointer_x = ret.getFirst();
//            pointer_y = ret.getSecond();
//            if (pointer_x == -1 || pointer_y == -1) {
//                System.out.println("没有搜到");
//                return false;
//            }
//            if (this.array[pointer_x][pointer_y] == target) {
//                System.out.printf("%d已找到，坐标(%d, %d)\n", this.target, pointer_x, pointer_y);
//                return true;
//            }
//        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] > target)
                    break;
                if (matrix[i][j] == target)
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        int[][] array = {{1, 4, 6, 8, 10, 12, 13},
//                {3, 5, 7, 8, 11, 13, 14},
//                {5, 6, 7, 9, 13, 15, 16},
//                {6, 9, 11, 12, 13, 16, 17}};
        int array[][] = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        System.out.println(new OfferFindNumberInMatrix().findNumberIn2DArray(array, 5));
    }
}
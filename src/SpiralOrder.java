import java.util.ArrayList;

public class SpiralOrder {
    public int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return new int[]{};
        var left = 0;
        var right = matrix[0].length - 1;
        var top = 0;
        var bottom = matrix.length - 1;
        var x = 0;
        var y = 0;
        var restCount = matrix.length * matrix[0].length;
        var clockwiseTraversal = new ArrayList<Integer>();
        while (restCount > 0 && left <= right && top <= bottom) {
            while (restCount > 0 && y <= right) {
                clockwiseTraversal.add(matrix[x][y]);
                restCount--;
                if (y == right)
                    break;
                y++;
            }
            top++;
            x++;
            while (restCount > 0 && x <= bottom) {
                clockwiseTraversal.add(matrix[x][y]);
                restCount--;
                if (x == bottom)
                    break;
                x++;
            }
            right--;
            y--;
            while (restCount > 0 && y >= left) {
                clockwiseTraversal.add(matrix[x][y]);
                restCount--;
                if (y == left)
                    break;
                y--;
            }
            bottom--;
            x--;
            while (restCount > 0 && x >= top) {
                clockwiseTraversal.add(matrix[x][y]);
                restCount--;
                if (x == top)
                    break;
                x--;
            }
            left++;
            y++;
        }
        return clockwiseTraversal.stream().mapToInt(Integer::intValue).toArray();
    }
}

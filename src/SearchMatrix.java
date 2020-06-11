import Util.Tuple;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static Util.test_script.binarySearch;
import static Util.test_script.speedtest;

public class SearchMatrix {
    public boolean searchMatrix_multiBiSearch(int[][] matrix, int target) {
        for (var line : matrix) {
            if (binarySearch(Arrays.stream(line).boxed().collect(Collectors.toList()), target) != -1)
                return true;
        }
        return false;
    }

    private Tuple<Integer> search(int left, int right, int top, int bottom, int target, int[][] matrix) {
        if (left > right || top > bottom)
            return new Tuple<>(-1, -1);
        int mid = (left + right) / 2;
        var row = top;
        while (row <= bottom && matrix[row][mid] <= target) {
            if (matrix[row][mid] == target)
                return new Tuple<>(row, mid);
            row++;
        }
        var searchLeftDown = search(left, mid - 1, row, bottom, target, matrix);
        if (searchLeftDown.equals(new Tuple<>(-1, -1)) == false)
            return searchLeftDown;
        var searchRightUp = search(mid + 1, right, top, row - 1, target, matrix);
        return searchRightUp;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        return !this.search(0, matrix[0].length - 1, 0, matrix.length - 1, target, matrix).equals(new Tuple<>(-1, -1));
    }

    public boolean searchMatrix_extremelyFast(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        var i = matrix.length - 1;
        var j = 0;
        while (i >= 0 && j < matrix[0].length) {
            if (matrix[i][j] == target)
                return true;
            else if (matrix[i][j] > target)
                i--;
            else if (matrix[i][j] < target)
                j++;
        }
        return false;
    }

    public static void main(String[] args) {
        var sol = new SearchMatrix();
        var functions = new Tuple<Function<Tuple<Object>, Boolean>>((tuple) -> sol.searchMatrix_multiBiSearch((int[][]) tuple.getFirst()
                , (int) tuple.getSecond()), (tuple) -> sol.searchMatrix((int[][]) tuple.getFirst(), (int) tuple.getSecond())
                , (tuple) -> sol.searchMatrix_extremelyFast((int[][]) tuple.getFirst(), (int) tuple.getSecond()));
        var funNames = new Tuple<String>("searchMatrix_multiBiSearch", "searchMatrix", "searchMatrix_extremelyFast");
        var argument = new Tuple<Object>(new int[][]{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}}, 21);
        speedtest(functions, funNames, argument);
        var matrix = new int[5000][5000];
        for (var i : IntStream.range(0, 5000).toArray())
            for (var j : IntStream.range(0, 5000).toArray())
                matrix[i][j] = i + j;
        speedtest(functions, funNames, new Tuple<Object>(matrix, 10000));
    }
}

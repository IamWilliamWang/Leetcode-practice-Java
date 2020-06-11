import java.util.HashMap;

public class MinDistance {
    class Point {
        public int i, j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private String word1, word2;
    private HashMap<Point, Integer> map = new HashMap<>();

    private int dp(int i, int j) {
        Point here = new Point(i, j);
        if (map.containsKey(here))
            return map.get(here);
        if (i == -1)
            return j + 1;
        if (j == -1)
            return i + 1;
        if (word1.charAt(i) == word2.charAt(j))
            return dp(i - 1, j - 1);
        int bp1 = 1 + dp(i - 1, j), bp2 = 1 + dp(i, j - 1), bp3 = 1 + dp(i - 1, j - 1);
        map.put(here, Math.min(Math.min(bp1, bp2), bp3));
        return map.get(here);
    }

    public int minDistanceRecursive(String word1, String word2) {
        this.word1 = word1;
        this.word2 = word2;
        return this.dp(word1.length() - 1, word2.length() - 1);
    }

    public int minDistance(String word1, String word2) {
        int rows = word1.length() + 1, cols = word2.length() + 1;
        int distanceMatrix[][] = new int[rows][cols];
        for (int j = 1; j < cols; j++)
            distanceMatrix[0][j] = j;
        for (int i = 1; i < rows; i++)
            distanceMatrix[i][0] = i;
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    distanceMatrix[i][j] = distanceMatrix[i - 1][j - 1];
                else {
                    int bp1 = 1 + distanceMatrix[i - 1][j], bp2 = 1 + distanceMatrix[i][j - 1], bp3 = 1 + distanceMatrix[i - 1][j - 1];
                    distanceMatrix[i][j] = Math.min(Math.min(bp1, bp2), bp3);
                }
            }
        }
        return distanceMatrix[word1.length()][word2.length()];
    }

    public static void main(String[] args) {
        System.out.println(new MinDistance().minDistance("This is a sentence.", "I can't solve it by myself."));
    }
}

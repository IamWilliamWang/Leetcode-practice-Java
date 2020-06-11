public class TwoSum {
    public int[] twoSum(int[] numbers, int target) {
        var i = 0;
        var j = numbers.length - 1;
        while (i < j) {
            if (numbers[i] + numbers[j] == target)
                return new int[]{i + 1, j + 1};
            else if (numbers[i] + numbers[j] > target)
                j--;
            else if (numbers[i] + numbers[j] < target)
                i++;
        }
        return new int[]{};
    }
}

import java.util.Arrays;
import java.util.stream.IntStream;

public class FindBestValue {
    public int findBestValue(int[] arr, int target) {
        if (arr == null || arr.length == 0)
            return 0;
        Arrays.sort(arr);
        var sum = 0;
        var i = 0;
        while (i < arr.length) {
            if (sum + (arr.length - i) * arr[i] > target)
                break;
            sum += arr[i];
            i++;
        }
        if (i == arr.length)
            return arr[i - 1];
        int[] potentialResultRange = new int[2];
        if (i == 0) {
            potentialResultRange[0] = target / arr.length;
            potentialResultRange[1] = potentialResultRange[0] + 1;
        } else {
            potentialResultRange[0] = arr[i - 1];
            potentialResultRange[1] = arr[i];
        }
        var minDistance = Integer.MAX_VALUE;
        var result = arr[arr.length - 1];
        for (var potentialResult : IntStream.range(potentialResultRange[0], potentialResultRange[1] + 1).toArray()) {
            if (Math.abs((sum + (arr.length - i) * potentialResult) - target) < minDistance) {
                minDistance = Math.abs(sum + (arr.length - i) * potentialResult - target);
                result = potentialResult;
            }
        }
        return result;
    }
}

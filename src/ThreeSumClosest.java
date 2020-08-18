import java.util.Arrays;
import java.util.stream.IntStream;

public class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        var closestSum = Integer.MAX_VALUE;
        var minDistance = Integer.MAX_VALUE;
        for (var i : IntStream.range(0, nums.length).toArray()) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            for (var j : IntStream.range(i + 1, nums.length).toArray()) {
                var k = nums.length - 1;
                if (j > i + 1 && nums[j] == nums[j - 1])
                    continue;
                while (j < k && nums[i] + nums[j] + nums[k] > target)
                    k--;
                if (j != k && target - (nums[i] + nums[j] + nums[k]) < minDistance) {
                    minDistance = target - (nums[i] + nums[j] + nums[k]);
                    closestSum = nums[i] + nums[j] + nums[k];
                }
                if (k < nums.length - 1 && nums[i] + nums[j] + nums[k + 1] - target < minDistance) {
                    minDistance = (nums[i] + nums[j] + nums[k + 1]) - target;
                    closestSum = nums[i] + nums[j] + nums[k + 1];
                }
            }
        }
        return closestSum;
    }
}
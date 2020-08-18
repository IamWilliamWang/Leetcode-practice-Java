import java.util.Arrays;
import java.util.stream.IntStream;

public class ThreeSumSmaller {
    public int threeSumSmaller(int[] nums, int target) {
        var result = 0;
        Arrays.sort(nums);
        for (var i : IntStream.range(0, nums.length).toArray()) {
            var k = nums.length - 1;
            for (var j : IntStream.range(i + 1, nums.length).toArray()) {
                while (j < k && nums[i] + nums[j] + nums[k] >= target)
                    k--;
                if (j == k)
                    break;
                if (nums[i] + nums[j] + nums[k] < target)
                    result += k - j;
            }
        }
        return result;
    }
}

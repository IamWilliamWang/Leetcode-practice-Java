import Util.Tuple;

import javax.swing.text.Utilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class FourSum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        var result = new ArrayList<List<Integer>>();
        for (var i : IntStream.range(0, nums.length).toArray()) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            for (var j : IntStream.range(i + 1, nums.length).toArray()) {
                if (j > i + 1 && nums[j] == nums[j - 1])
                    continue;
                var l = nums.length - 1;
                for (var k : IntStream.range(j + 1, nums.length).toArray()) {
                    if (k > j + 1 && nums[k] == nums[k - 1])
                        continue;
                    while (k < l && nums[i] + nums[j] + nums[k] + nums[l] > target)
                        l--;
                    if (k == l)
                        break;
                    if (nums[i] + nums[j] + nums[k] + nums[l] == target)
                        result.add(new Tuple<>(nums[i], nums[j], nums[k], nums[l]));
                }
            }
        }
        return result;
    }
}

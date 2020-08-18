import Util.Heap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class FindKthLargest {
    public int findKthLargest(int[] nums, int k) {
        if (k > nums.length)
            return 0;
        List<Integer> numList = Arrays.stream(nums).boxed().collect(Collectors.toList());
        if (k < nums.length / 2) {
            List<Integer> nlargest = new Heap<>(numList).nlargest(k);
            return nlargest.get(nlargest.size() - 1);
        } else {
            List<Integer> nsmallest = new Heap<>(numList).nsmallest(nums.length - k + 1);
            return nsmallest.get(nsmallest.size() - 1);
        }
    }
}
import java.util.HashSet;

public class LongestConsecutiveHashSet {
    public int longestConsecutive(int[] nums) {
        var numsSet = new HashSet<Integer>();
        var length = 0;
        var maxLength = 0;
        for (var num : nums)
            numsSet.add(num);
        for (var num : numsSet) {
            if (!numsSet.contains(num - 1)) {
                length = 0;
                while (numsSet.contains(num)) {
                    length++;
                    num++;
                }
                maxLength = Math.max(maxLength, length);
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(new LongestConsecutiveHashSet().longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
    }
}

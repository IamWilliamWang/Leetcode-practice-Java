import Util.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class ReversePairs {
    public int reversePairs(int[] nums) {
        int ans = 0;
        ArrayList<Integer> table = new ArrayList<>();
        for (int num : nums) {
            int insertIndex = Collections.binarySearch(table, num);
            if (insertIndex >= 0) { // 需要特殊处理，直到指向大于num的第一个位置
                while (insertIndex < table.size() && table.get(insertIndex) == num)
                    insertIndex++;
            } else {
                insertIndex = -insertIndex - 1;
            }
            table.add(insertIndex, num);
            ans += table.size() - insertIndex - 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        var list = new Tuple<>(1, 2, 3, 3, 3, 3, 3, 3, 4);
        System.out.print(Collections.binarySearch(list, 3));
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Jump {
    public int jump(int[] nums) {
        if (nums.length == 0)
            return 0;

        int[] jumpList = new int[nums.length];
        Arrays.fill(jumpList, -1);
        jumpList[nums.length - 1] = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            ArrayList<Integer> answer = new ArrayList<>();
            for (int step = nums[i]; step > 0; step--) {
                if ((i + step) < nums.length && jumpList[i + step] != -1)
                    answer.add(jumpList[i + step]);
            }
            if (answer.isEmpty())
                jumpList[i] = -1;
            else
                jumpList[i] = 1 + Collections.min(answer);
        }
        return jumpList[0];
    }

    public static void main(String[] args) {
        System.out.println(new Jump().jump(new int[] {2,3,1,1,4}));
    }
}

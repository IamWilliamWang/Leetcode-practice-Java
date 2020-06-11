import java.util.*;
import java.util.stream.Collectors;

public class FirstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0)
            return 1;
        List<Integer> numList = Arrays.stream(nums).boxed().collect(Collectors.toList());
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(numList);
        while (!priorityQueue.isEmpty() && priorityQueue.peek() <= 0)
            priorityQueue.poll();
        int shouldBe = 1;
        while (!priorityQueue.isEmpty()) {
            if (shouldBe < priorityQueue.peek())
                return shouldBe;
            if (shouldBe == priorityQueue.peek())
                shouldBe++;
            priorityQueue.poll();
        }
        return shouldBe;
    }

    public static void main(String[] args) {
        System.out.println(new FirstMissingPositive().firstMissingPositive(new int[]{0, 2, 2, 1, 1}));
    }
}

import java.util.Stack;
import java.util.stream.IntStream;

public class DailyTemperatures {
    public int[] dailyTemperatures(int[] T) {
        var stackOfIndex = new Stack<Integer>();
        int[] result = new int[T.length];
        for (var i : IntStream.range(0, T.length).toArray()) {
            var temperature = T[i];
            while (!stackOfIndex.isEmpty() && T[stackOfIndex.peek()] < temperature) {
                int j = stackOfIndex.pop();
                result[j] = i - j;
            }
            stackOfIndex.push(i);
        }
        return result;
    }
}

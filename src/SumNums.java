import java.util.stream.IntStream;

public class SumNums {
    public int sumNums(int n) {
        return IntStream.range(1, n + 1).sum();
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KidsWithCandies {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        var maxCandy = Arrays.stream(candies).max().getAsInt();
        var result = new ArrayList<Boolean>();
        for (var candy : candies)
            result.add(candy + extraCandies >= maxCandy ? true : false);
        return result;
    }
}

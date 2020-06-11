import java.util.stream.IntStream;

public class IsPalindrome {
    public boolean isPalindrome(int x) {
        var xStr = Integer.valueOf(x).toString();
        for (var i : IntStream.range(0, xStr.length() / 2 + 1).toArray()) {
            if (xStr.charAt(i) != xStr.charAt(xStr.length() - i - 1))
                return false;
        }
        return true;
    }
}
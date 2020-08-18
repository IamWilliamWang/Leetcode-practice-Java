import java.util.Arrays;
import java.util.stream.IntStream;

public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        StringBuilder prefix = new StringBuilder();
        int zipLength = Arrays.stream(strs).mapToInt(String::length).min().getAsInt();
        toEnd:
        for (var i : IntStream.range(0, zipLength).toArray()) {
            for (var str : strs) {
                if (str.charAt(i) != strs[0].charAt(i))
                    break toEnd;
            }
            prefix.append(strs[0].charAt(i));
        }
        return prefix.toString();
    }
}

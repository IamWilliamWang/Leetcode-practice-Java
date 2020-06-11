import Util.TreeNode;
import Util.TreeUtil;
import Util.Tuple;

import static Util.test_script.speedtest;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LongestConsecutive {
    private int longest;

    private int detectLongestPath(TreeNode<Integer> root, Integer valIs) {
        if (root == null || !root.val.equals(valIs))
            return 0;
        var leftLongestPathLength = detectLongestPath(root.left, valIs + 1);
        var rightLongestPathLength = detectLongestPath(root.right, valIs + 1);
        return Math.max(leftLongestPathLength, rightLongestPathLength) + 1;
    }

    public int longestConsecutive_clumsy(TreeNode<Integer> root) {
        longest = 0;
        var it = TreeUtil.inOrderTraversalIterator(root);
        while (it.hasNext()) {
            var node = it.next();
            var longestLocal = detectLongestPath(node, node.val);
            if (longestLocal > longest)
                longest = longestLocal;
        }
        return longest;
    }

    private void detectLongestPath(TreeNode<Integer> root, Integer valIs, int nowLength) {
        if (root == null)
            return;
        if (!root.val.equals(valIs)) {
            nowLength = 1;
            valIs = root.val;
        } else {
            nowLength++;
        }
        longest = Math.max(longest, nowLength);
        detectLongestPath(root.left, valIs + 1, nowLength);
        detectLongestPath(root.right, valIs + 1, nowLength);
    }

    public int longestConsecutive(TreeNode<Integer> root) {
        longest = 0;
        if (root == null)
            return 0;
        detectLongestPath(root, root.val, 0);
        return longest;
    }

    public static void main(String[] args) {
        var sol = new LongestConsecutive();
        var functions = new Tuple<Function<TreeNode, Integer>>(sol::longestConsecutive, sol::longestConsecutive_clumsy);
        var funcNames = new Tuple<>("longestConsecutive", "longestConsecutive_clumsy");
        var argument = TreeUtil.createTreeByPreInOrder(Stream.of(1, 3, 2, 4, 5).collect(Collectors.toList()), Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList()));
        speedtest(functions, funcNames, argument);
        argument = TreeUtil.createTreeByPreInOrder(Stream.of(2, 3, 2, 1).collect(Collectors.toList()), Stream.of(2, 1, 2, 3).collect(Collectors.toList()));
        speedtest(functions, funcNames, argument);
        argument = TreeUtil.createTreeByPreInOrder(IntStream.range(1, 5001).boxed().collect(Collectors.toList()), IntStream.range(1, 5001).boxed().collect(Collectors.toList()));
        speedtest(functions, funcNames, argument);
    }
}

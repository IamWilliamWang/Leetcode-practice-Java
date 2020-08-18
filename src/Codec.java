import Util.TreeNode;
import Util.TreeUtil;
import Util.Tuple;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode<Integer> root) {
        return TreeUtil.toValHeapToDict(root).toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("{}"))
            return null;
        if (!data.startsWith("{") || !data.endsWith("}"))
            return null;
        var dictionary = new HashMap<BigInteger, Integer>();
        StringBuilder builder = new StringBuilder(data);
        builder.deleteCharAt(0);
        int fromIndex = 0, toIndex = -1;
        while (fromIndex < builder.length()) {
            toIndex = builder.indexOf(",", fromIndex) + 1;
            if (toIndex == 0)
                toIndex = builder.length();
            var entryStr = builder.substring(fromIndex, toIndex - 1);
            fromIndex = toIndex;
            var key = new BigInteger(entryStr.split("=")[0].trim());
            var value = Integer.parseInt(entryStr.split("=")[1].trim());
            dictionary.put(key, value);
        }
        return TreeUtil.createTreeByHeapExpressedByDict(dictionary);
    }

    public static void main(String[] args) {
        var root = new TreeNode<>(1);
        var node = root;
        for (var i : IntStream.range(2, 1001).toArray()) {
            node.right = new TreeNode<>(i);
            node = node.right;
        }
        System.out.println(new Codec().deserialize(new Codec().serialize(root)));
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
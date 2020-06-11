import Util.TreeNode;

/**
 * Definition for a binary tree node.
 */
//class TreeNode {
//    int val;
//    TreeNode left;
//    TreeNode right;
//
//    TreeNode(int x) {
//        val = x;
//    }
//}

public class IsSymmetric {
    private boolean equal(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null)
            return true;
        if (node1 == null || node2 == null)
            return false;
        return node1.val == node2.val && equal(node1.left, node2.right) && equal(node1.right, node2.left);
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return equal(root.left, root.right);
    }
}

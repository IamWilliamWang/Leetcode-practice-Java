package Util;

import java.util.Iterator;

public class TreeNode<T> implements Iterable<TreeNode<T>> {
    public T val;
    public TreeNode<T> left, right;

    public TreeNode(T x) {
        this.val = x;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<TreeNode<T>> iterator() {
        return TreeUtil.preOrderTraversalIterator(this);
    }

    @Override
    public String toString() {
        return TreeUtil.toString(this);
    }
}

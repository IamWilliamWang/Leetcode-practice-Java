package Util;

import java.util.Iterator;

public class ListNode<T> implements Iterable<T> {
    public T val;
    public ListNode<T> next;

    public ListNode(T x) {
        this.val = x;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return ListUtil.iterator(this);
    }
}

package Util;

import java.util.*;
import java.util.stream.Collectors;

public class ListUtil {
    /**
     * 创建<bold>有头节点</bold>的单向链表。头节点存储链表长度
     * @param valList
     * @return
     */
    public static ListNode<Integer> createListWithHead(List<Integer> valList) {
        ListNode<Integer> head = new ListNode<>(valList.size());
        var previous = head;
        for (var val : valList) {
            var node = new ListNode<>(val);
            previous.next = node;
            previous = previous.next;
        }
        return head;
    }

    /**
     * 创建<bold>有头节点</bold>的单向链表。头节点存储链表长度
     * @param valList
     * @return
     */
    public static ListNode<Integer> createListWithHead(int[] valList) {
        return createListWithHead(Arrays.stream(valList).boxed().collect(Collectors.toList()));
    }

    /**
     * 创建<bold>没有头节点</bold>的单向链表。头节点存储链表长度
     * @param valList
     * @return
     */
    public static ListNode<Integer> createListWithoutHead(List<Integer> valList) {
        var head = new ListNode<>(valList.size());
        var previous = head;
        for (var val : valList) {
            var node = new ListNode<>(val);
            previous.next = node;
            previous = previous.next;
        }
        return head.next;
    }

    /**
     * 创建<bold>没有头节点</bold>的单向链表。头节点存储链表长度
     * @param valList
     * @return
     */
    public static ListNode<Integer> createListWithoutHead(int[] valList) {
        return createListWithoutHead(Arrays.stream(valList).boxed().collect(Collectors.toList()));
    }

    /**
     * 链表的迭代器
     * @param headNode 头节点
     * @param <T>
     * @return
     */
    public static <T> Iterator<T> iterator(ListNode<T> headNode) {
        return new Iterator<T>() {
            ListNode<T> nowNode = headNode;
            ListNode<T> previous;

            /**
             * Returns {@code true} if the iteration has more elements.
             * (In other words, returns {@code true} if {@link #next} would
             * return an element rather than throwing an exception.)
             *
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return nowNode.next != null;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             */
            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                var nextElement = nowNode.next.val;
                if (previous == null || previous.next != nowNode.next)
                    previous = nowNode;
                nowNode = nowNode.next;
                return nextElement;
            }

            /**
             * Removes from the underlying collection the last element returned
             * by this iterator (optional operation).  This method can be called
             * only once per call to {@link #next}.
             * <p>
             * The behavior of an iterator is unspecified if the underlying collection
             * is modified while the iteration is in progress in any way other than by
             * calling this method, unless an overriding class has specified a
             * concurrent modification policy.
             * <p>
             * The behavior of an iterator is unspecified if this method is called
             * after a call to the {@link #forEachRemaining forEachRemaining} method.
             *
             * @throws UnsupportedOperationException if the {@code remove}
             *                                       operation is not supported by this iterator
             * @throws IllegalStateException         if the {@code next} method has not
             *                                       yet been called, or the {@code remove} method has already
             *                                       been called after the last call to the {@code next}
             *                                       method
             * @implSpec The default implementation throws an instance of
             * {@link UnsupportedOperationException} and performs no other action.
             */
            @Override
            public void remove() {
                if (nowNode == null)
                    return;
                previous.next = nowNode.next;
            }
        };
    }

    /**
     * 链表的迭代器（切片后）
     * @param headNode 头节点
     * @param start    迭代器从第几个开始
     * @param <T>
     * @return
     */
    public static <T> Iterator<T> iterator(ListNode<T> headNode, int start) {
        var it = ListUtil.iterator(headNode);
        for (int i = 0; i < start && it.hasNext(); i++) {
            it.next();
        }
        return it.hasNext() ? it : null;
    }

    /**
     * 遍历<bold>有头节点</bold>的单向链表，返回遍历的结果
     * @param headNode 无头链表的第一个节点
     * @param <T>
     * @return
     */
    public static <T> List<T> traverse(ListNode<T> headNode) {
        var iterator = headNode.iterator();
        List<T> result = new ArrayList<>();
        while (iterator.hasNext())
            result.add(iterator.next());
        return result;
    }

    /**
     * 用于测试新方法的正确性
     * @param args
     */
    public static void main(String[] args) {
        // 测试createListWithHead
        System.out.println("------ testing createListWithHead ------");
        var nodes = new ArrayList<Integer>();
        Collections.addAll(nodes, 8, 7, 6, 5, 4, 3, 2, 1);
        var head = createListWithHead(nodes);
        head = createListWithHead(new int[] {8, 7, 6, 5, 4, 3, 2, 1});
        // 测试traverse
        System.out.println("------ testing traverse ------");
        System.out.println(ListUtil.traverse(head));
        // 测试iterator
        System.out.println("------ testing iterator ------");
        var it = ListUtil.iterator(head);
        while (it.hasNext())
            System.out.print(it.next() + ", ");
        System.out.println();
        // 测试iterator(islice)
        System.out.println("------ testing iterator(islice) ------");
        var itSlice = ListUtil.iterator(head, 6);
        while (itSlice.hasNext())
            System.out.print(itSlice.next() + ", ");
        System.out.println();
    }
}

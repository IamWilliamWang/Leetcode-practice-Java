package Util;

import java.util.List;

/**
 * Bisection algorithms.
 */
class Bisect {
    /**
     * @see #insortRight(List, int, int, int)
     */
    public static void insort(List<Integer> a, int x) {
        insortRight(a, x);
    }

    /**
     * @see #insortRight(List, int, int, int)
     */
    public static void insort(List<Integer> a, int x, int lo, int hi) {
        insortRight(a, x, lo, hi);
    }

    /**
     * @see #bisectRight(List, int, int, int)
     */
    public static void bisect(List<Integer> a, int x) {
        bisectRight(a, x);
    }

    /**
     * @see #bisectRight(List, int, int, int)
     */
    public static void bisect(List<Integer> a, int x, int lo, int hi) {
        bisectRight(a, x, lo, hi);
    }

    /**
     * @see #insortRight(List, int, int, int)
     */
    public static void insortRight(List<Integer> a, int x) {
        insortRight(a, x, 0, -1);
    }

    /**
     * Insert item x in list a, and keep it sorted assuming a is sorted.
     * <p>
     * If x is already in a, insert it to the right of the rightmost x.
     * <p>
     * Optional args lo (default 0) and hi (default len(a)) bound the slice of a to be searched.
     */
    public static void insortRight(List<Integer> a, int x, int lo, int hi) {
        a.add(bisectRight(a, x, lo, hi), x);
    }

    /**
     * @see #bisectRight(List, int, int, int)
     */
    public static int bisectRight(List<Integer> a, int x) {
        return bisectRight(a, x, 0, -1);
    }

    /**
     * Return the index where to insert item x in list a, assuming a is sorted.
     * <p>
     * The return value i is such that all e in a[:i] have e <= x, and all e in
     * a[i:] have e > x.  So if x already appears in the list, a.insert(x) will
     * insert just after the rightmost x already there.
     * <p>
     * Optional args lo (default 0) and hi (default len(a)) bound the
     * slice of a to be searched.
     */
    public static int bisectRight(List<Integer> a, int x, int lo, int hi) {
        if (lo < 0)
            throw new IllegalArgumentException("lo must be non-negative");
        if (hi == -1)
            hi = a.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (x < a.get(mid))
                hi = mid;
            else
                lo = mid + 1;
        }
        return lo;
    }

    /**
     * @see #insortLeft(List, int, int, int)
     */
    public static void insortLeft(List<Integer> a, int x) {
        insortLeft(a, x, 0, -1);
    }

    /**
     * Insert item x in list a, and keep it sorted assuming a is sorted.
     * <p>
     * If x is already in a, insert it to the left of the leftmost x.
     * <p>
     * Optional args lo (default 0) and hi (default len(a)) bound the
     * slice of a to be searched.
     */
    public static void insortLeft(List<Integer> a, int x, int lo, int hi) {
        a.add(bisectLeft(a, x, lo, hi), x);
    }

    /**
     * @see #bisectLeft(List, int, int, int)
     */
    public static int bisectLeft(List<Integer> a, int x) {
        return bisectLeft(a, x, 0, -1);
    }

    /**
     * Return the index where to insert item x in list a, assuming a is sorted.
     * <p>
     * The return value i is such that all e in a[:i] have e < x, and all e in
     * a[i:] have e >= x.  So if x already appears in the list, a.insert(x) will
     * insert just before the leftmost x already there.
     * <p>
     * Optional args lo (default 0) and hi (default len(a)) bound the
     * slice of a to be searched.
     */
    public static int bisectLeft(List<Integer> a, int x, int lo, int hi) {
        if (lo < 0)
            throw new IllegalArgumentException("lo must be non-negative");
        if (hi == -1)
            hi = a.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (a.get(mid) < x)
                lo = mid + 1;
            else
                hi = mid;
        }
        return lo;
    }
}

public class BisectUtil {
    /**
     * 在collection中搜索x出现的最左边的位置，没找到则返回-1
     *
     * @param collection
     * @param x
     * @return
     */
    public static int indexOf(List<Integer> collection, int x) {
        var i = Bisect.bisectLeft(collection, x);
        if (i != collection.size() && collection.get(i) == x)
            return i;
        return -1;
    }

    /**
     * 返回collection中max(小于x的数字)。如果没找到任何数字则产生IndexOutOfBoundsException
     *
     * @param collection
     * @param x
     * @return
     */
    public static int findLt(List<Integer> collection, int x) {
        var i = Bisect.bisectLeft(collection, x);
        if (i != 0)
            return collection.get(i - 1);
        throw new IndexOutOfBoundsException();
    }

    /**
     * 返回collection中max(小于等于x的数字)。如果没找到任何数字则产生IndexOutOfBoundsException
     *
     * @param collection
     * @param x
     * @return
     */
    public static int findLe(List<Integer> collection, int x) {
        var i = Bisect.bisectRight(collection, x);
        if (i != 0)
            return collection.get(i - 1);
        throw new IndexOutOfBoundsException();
    }

    /**
     * 返回collection中min(大于x的数字)。如果没找到任何数字则产生IndexOutOfBoundsException
     *
     * @param collection
     * @param x
     * @return
     */
    public static int findGt(List<Integer> collection, int x) {
        var i = Bisect.bisectRight(collection, x);
        if (i != collection.size())
            return collection.get(i);
        throw new IndexOutOfBoundsException();
    }

    /**
     * 返回collection中min(大于等于x的数字)。如果没找到任何数字则产生IndexOutOfBoundsException
     *
     * @param collection
     * @param x
     * @return
     */
    public static int findGe(List<Integer> collection, int x) {
        var i = Bisect.bisectLeft(collection, x);
        if (i != collection.size())
            return collection.get(i);
        throw new IndexOutOfBoundsException();
    }
}

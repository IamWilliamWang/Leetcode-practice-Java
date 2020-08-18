package Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ComparableTuple<T extends Comparable<T>> extends ArrayList<T> implements Collection<T>, List<T>, Iterable<T>, Comparable<List<T>> {
    @SafeVarargs
    public ComparableTuple(T... args) {
        Collections.addAll(this, args);
    }

    public T getFirst() {
        return getElementAt(0);
    }

    public T getSecond() {
        return getElementAt(1);
    }

    public T getThird() {
        return getElementAt(2);
    }

    public T getElementAt(int index) {
        if (this.size() >= index + 1)
            return this.get(index);
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparableTuple) {
            ComparableTuple<T> tuple = (ComparableTuple<T>) obj;
            if (tuple.size() != this.size())
                return false;
            for (var i : IntStream.range(0, tuple.size()).toArray()) {
                if (!tuple.get(i).equals(this.get(i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param tList the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(List<T> tList) {
        int i;
        for (i = 0; i < this.size() && i < tList.size(); i++) {
            int cmp = this.get(i).compareTo(tList.get(i));
            if (cmp != 0)
                return cmp;
        }
        return this.size() - tList.size();
    }

    @Override
    public String toString() {
        return super.toString().replace("[", "(").replace("]", ")");
    }
}

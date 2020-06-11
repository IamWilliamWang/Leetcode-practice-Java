package Util;

import java.util.*;
import java.util.stream.IntStream;

public class Tuple<T> extends ArrayList<T> implements Collection<T>, List<T>, Iterable<T> {
    @SafeVarargs
    public Tuple(T... args) {
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
        try {
            Tuple<T> tuple = (Tuple<T>) obj;
            if (tuple.size() != this.size())
                return false;
            for (var i : IntStream.range(0, tuple.size()).toArray()) {
                if (!tuple.get(i).equals(this.get(i)))
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

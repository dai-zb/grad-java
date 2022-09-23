package com.dzb.math.data.tuple;

import lombok.Getter;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 9:42
 */
public class Tuple {

    @Getter
    private final Object[] array;

    public Tuple(Object[] array) {
        this.array = array;
        if (array.length < size()) {
            throw new IllegalArgumentException("args length is " + array.length + ", args length < " + size());
        }
    }

    public int size() {
        return getArray().length;
    }

    public Object get(int idx) {
        if (idx >= size()) {
            throw new IllegalArgumentException("index is " + idx + ", index >= " + size());
        }
        return getArray()[idx];
    }

    public void set(int idx, Object obj) {
        if (idx >= size()) {
            throw new IllegalArgumentException("index is " + idx + ", index >= " + size());
        }
        getArray()[idx] = obj;
    }

    public void update(Object... obj) {
        if (obj.length < size()) {
            throw new IllegalArgumentException("args length is " + obj.length + ", args length < Tuple size");
        }
        for (int i = 0; i < size(); i++) {
            set(i, obj[i]);
        }
    }

    @Override
    public String toString() {
        Object[] a = getArray();

        int iMax = a.length - 1;
        if (iMax == -1)
            return "()";

        StringBuilder b = new StringBuilder();
        b.append('(');
        for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax)
                return b.append(')').toString();
            b.append(", ");
        }
    }
}

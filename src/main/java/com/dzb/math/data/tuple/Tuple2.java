package com.dzb.math.data.tuple;

import com.dzb.math.data.tupleSimple.SmpTuple2;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-6-3 10:54
 */
public class Tuple2<T0, T1> extends Tuple {

    public T0 _0() {
        return (T0) get(0);
    }

    public void _0(T0 t) {
        set(0, t);
    }

    public T1 _1() {
        return (T1) get(1);
    }

    public void _1(T1 t) {
        set(1, t);
    }

    @Override
    public int size() {
        return 2;
    }

    public Tuple2(T0 _0, T1 _1) {
        super(new Object[]{_0, _1});
    }

    public Tuple2(Object[] array) {
        super(array);
    }

    public SmpTuple2<T0, T1> simple2() {
        return new SmpTuple2<>(_0(), _1());
    }
}

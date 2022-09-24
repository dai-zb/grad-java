package com.dzb.math.data.tuple;

import com.dzb.math.data.tupleSimple.SmpTuple3;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 10:56
 */
public class Tuple3<T0, T1, T2> extends Tuple2<T0, T1> {

    public T2 _2() {
        return (T2) get(2);
    }

    public void _2(T2 t) {
        set(2, t);
    }

    @Override
    public int size() {
        return 3;
    }

    public Tuple3(T0 _0, T1 _1, T2 _2) {
        super(new Object[]{_0, _1, _2});
    }

    public Tuple3(Object[] array) {
        super(array);
    }

    public SmpTuple3<T0, T1, T2> simple3() {
        return new SmpTuple3<>(_0(), _1(), _2());
    }
}

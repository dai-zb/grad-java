package com.dzb.math.data.tuple;

import com.dzb.math.data.tupleSimple.SmpTuple4;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 10:56
 */
public class Tuple4<T0, T1, T2, T3> extends Tuple3<T0, T1, T2> {

    public T3 _3() {
        return (T3) get(3);
    }

    public void _3(T3 t) {
        set(3, t);
    }

    @Override
    public int size() {
        return 4;
    }

    public Tuple4(T0 _0, T1 _1, T2 _2, T3 _3) {
        super(new Object[]{_0, _1, _2, _3});
    }

    public Tuple4(Object[] array) {
        super(array);
    }

    public SmpTuple4<T0, T1, T2, T3> simple4() {
        return new SmpTuple4<>(_0(), _1(), _2(), _3());
    }
}

package com.dzb.math.data.tupleSimple;

import com.dzb.math.data.tuple.Tuple2;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-03-14 16:39
 */
public class SmpTuple2<T0,T1> {
    public T0 _0;
    public T1 _1;

    public SmpTuple2(T0 _0, T1 _1) {
        this._0 = _0;
        this._1 = _1;
    }

    public Tuple2<T0, T1> enhance() {
        return new Tuple2<>(_0, _1);
    }

    @Override
    public String toString() {
        return "(" + _0 +
                ", " + _1 +
                ')';
    }
}

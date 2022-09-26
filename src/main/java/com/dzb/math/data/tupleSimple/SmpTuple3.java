package com.dzb.math.data.tupleSimple;

import com.dzb.math.data.tuple.Tuple3;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-3-22 20:10
 */
public class SmpTuple3<T0, T1, T2> {
    public T0 _0;
    public T1 _1;
    public T2 _2;

    public SmpTuple3(T0 _0, T1 _1, T2 _2) {
        this._0 = _0;
        this._1 = _1;
        this._2 = _2;
    }

    public Tuple3<T0, T1, T2> enhance() {
        return new Tuple3<>(_0, _1, _2);
    }

    @Override
    public String toString() {
        return "(" + _0 +
                ", " + _1 +
                ", " + _2 +
                ')';
    }
}

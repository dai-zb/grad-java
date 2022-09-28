package com.dzb.math.data.tupleSimple;

import com.dzb.math.data.tuple.Tuple4;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-6-3 11:41
 */
public class SmpTuple4<T0, T1, T2, T3> {
    public T0 _0;
    public T1 _1;
    public T2 _2;
    public T3 _3;

    public SmpTuple4(T0 _0, T1 _1, T2 _2, T3 _3) {
        this._0 = _0;
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    public Tuple4<T0, T1, T2, T3> enhance() {
        return new Tuple4<>(_0, _1, _2, _3);
    }

    @Override
    public String toString() {
        return "(" + _0 +
                ", " + _1 +
                ", " + _2 +
                ", " + _3 +
                ')';
    }
}

package com.dzb.math.utils;

import com.dzb.math.auto2.func.Conv3DFunc;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-24 14:21
 */
public abstract class MatrixFunction {

    public static DoubleMatrix relu(DoubleMatrix x) {
        DoubleMatrix mask = x.ge(0);
        return x.mul(mask);
    }

    public static DoubleMatrix sigmoid(DoubleMatrix x) {
        DoubleMatrix y = DoubleMatrix.zeros(x.rows, x.columns);
        for (int i = 0; i < x.rows; i++) {
            for (int j = 0; j < x.columns; j++) {
                double v = x.get(i, j);
                v = 1 / (1 + Math.exp(-1 * v));
                y.put(i, j, v);
            }
        }
        return y;
    }

//    // inplace
//    public static DoubleMatrix sigmoidi(DoubleMatrix x) {
//
//        throw new RuntimeException("未完成");
//        // return x;
//    }

    public static DoubleMatrix exp(DoubleMatrix x) {
        DoubleMatrix y = DoubleMatrix.zeros(x.rows, x.columns);

        for (int i = 0; i < x.rows; i++) {
            for (int j = 0; j < x.columns; j++) {
                double v = x.get(i, j);
                if (v > 700) // 防止出现无穷
                    v = 700;
                v = Math.exp(v);
                y.put(i, j, v);
            }
        }
        return y;
    }

    public static DoubleMatrix log(DoubleMatrix x) {
        DoubleMatrix y = DoubleMatrix.zeros(x.rows, x.columns);

        for (int i = 0; i < x.rows; i++) {
            for (int j = 0; j < x.columns; j++) {
                double v = x.get(i, j);
                if (v < 1E-320) // 防止出现无穷
                    v = 1E-320;
                v = Math.log(v);
                y.put(i, j, v);
            }
        }
        return y;
    }

    // inplace
    public static DoubleMatrix logi(DoubleMatrix x) {
        for (int i = 0; i < x.rows; i++) {
            for (int j = 0; j < x.columns; j++) {
                double v = x.get(i, j);
                v = Math.log(v);
                x.put(i, j, v);
            }
        }
        return x;
    }

    // 1表示在行的方向进行操作
    public static DoubleMatrix sum1(DoubleMatrix x) {
        DoubleMatrix y = DoubleMatrix.zeros(x.rows, 1);

        for (int i = 0; i < x.rows; i++) {
            double v = 0;
            for (int j = 0; j < x.columns; j++) {
                v += x.get(i, j);
            }
            y.put(i, 0, v);
        }
        return y;
    }

    // 1表示在行的方向进行操作
    public static DoubleMatrix softmax1(DoubleMatrix x) {
        DoubleMatrix e = exp(x);
        DoubleMatrix s = sum1(e);

        for (int i = 0; i < e.rows; i++) {
            for (int j = 0; j < e.columns; j++) {
                double v = e.get(i, j);
                double vv = s.get(i, 0);
                e.put(i, j, v / vv);
            }
        }
        return e;
    }

//    // inplace
//    public static DoubleMatrix softmaxi(DoubleMatrix x) {
//
//        throw new RuntimeException("未完成");
//        // return x;
//    }

    public static DoubleMatrix getColumns(DoubleMatrix x, int[] idx) {
        int rows = x.rows;
        int columns = idx.length;
        DoubleMatrix matrix = DoubleMatrix.zeros(rows, columns);

        DoubleMatrix tmp = new DoubleMatrix(rows, 1);
        for (int i = 0; i < idx.length; i++) {
            DoubleMatrix column = x.getColumn(idx[i], tmp);
            matrix.putColumn(i, column);
        }
        return matrix;
    }

    // 将a的列，根据idx的索引，添加覆盖到x之中
    public static DoubleMatrix putColumns(DoubleMatrix x, DoubleMatrix a, int[] idx) {
        // DoubleMatrix matrix = x.dup();

        DoubleMatrix tmp = new DoubleMatrix(a.rows, 1);
        for (int i = 0; i < idx.length; i++) {
            x.putColumn(idx[i], a.getColumn(i, tmp));
        }
        return x;
    }
}

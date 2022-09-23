package com.dzb.math.utils;

import com.dzb.math.data.tupleSimple.SmpTuple2;

import java.util.List;

public class LoadData {

    public static SmpTuple2<double[][], int[]> load(String path) {
        Csv<Integer[]> csv = new Csv<>(path,
                str -> {
                    String[] split = str.split(",");
                    Integer[] ints = new Integer[split.length];
                    for (int i = 0; i < split.length; i++) {
                        ints[i] = Integer.parseInt(split[i]);
                    }
                    return ints;
                });
        List<Integer[]> list = csv.asList();
        int rows = list.size();
        int columns = list.get(0).length;
        int[] y = new int[rows];
        double[][] x = new double[rows][columns - 1];

        for (int i = 0; i < rows; i++) {
            y[i] = list.get(i)[0];
            for (int j = 1; j < columns - 1; j++) {
                x[i][j] = list.get(i)[j];
            }
        }
        return new SmpTuple2<>(x, y);
    }

    public static int[] split(int[] x, int start, int len) {
        int[] y = new int[len];
        if (len >= 0) System.arraycopy(x, start, y, 0, len);
        return y;
    }

    public static double[][] split(double[][] x, int start, int len) {
        double[][] y = new double[len][x[0].length];
        for (int i = 0; i < len; i++) {
            System.arraycopy(x[i + start], 0, y[i], 0, x[0].length);
        }
        return y;
    }

    // 将向量扩展为one-hot的矩阵
    public static double[][] expand(int[] x, int columns) {
        int rows = x.length;
        double[][] y = new double[rows][columns];
        for (int i = 0; i < x.length; i++) {
            y[i][x[i]] = 1;
        }
        return y;
    }
}

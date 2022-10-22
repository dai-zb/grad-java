package com.dzb.math.auto3.module;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.GetColumnsFunc;
import com.dzb.math.auto2.func.PutColumnsFunc;
import com.dzb.math.auto2.func.matrix.DotFunc;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.List;

// 计算效率很低，不要使用
// 会大量占用内存
// 替代使用Conv3DFunc
public class Conv3D implements Module {
    private final int inB;
    private final int inC;
    private final int inH;
    private final int inW;
    private final int kernelC;

    private final Variable W;
    private final List<Variable> vars = new ArrayList<>();

    public Conv3D(int inB,int inC, int inH, int inW, int kernelC, double wScaler) {
        // 输入图片的尺寸 C, H(行), W(列)
        this.inB = inB;
        this.inC = inC;
        this.inH = inH;
        this.inW = inW;
        // 卷积核的通道 (3x3的卷积核)
        this.kernelC = kernelC;

        DoubleMatrix w = DoubleMatrix.randn(inC * 3 * 3, kernelC);

        w.muli(wScaler);
        W = new Variable(w);
    }

    @Override
    public Variable build(Variable x) {
        int cc = kernelC * (inH - 2) * (inW - 2);

        Variable y = new Constant(DoubleMatrix.zeros(inB, cc));

        for (int i = 0; i < inH - 2; i++) {
            for (int j = 0; j < inW - 2; j++) {
                int[] ints = new int[inC * 3 * 3];
                for (int c = 0; c < inC; c++) {
                    int idx00 = c * (inH * inW) + i * inW + j;
                    ints[c * 9] = idx00;
                    int idx01 = c * (inH * inW) + i * inW + j + 1;
                    ints[c * 9 + 1] = idx01;
                    int idx02 = c * (inH * inW) + i * inW + j + 2;
                    ints[c * 9 + 2] = idx02;
                    int idx10 = c * (inH * inW) + (i + 1) * inW + j;
                    ints[c * 9 + 3] = idx10;
                    int idx11 = c * (inH * inW) + (i + 1) * inW + j + 1;
                    ints[c * 9 + 4] = idx11;
                    int idx12 = c * (inH * inW) + (i + 1) * inW + j + 2;
                    ints[c * 9 + 5] = idx12;
                    int idx20 = c * (inH * inW) + (i + 2) * inW + j;
                    ints[c * 9 + 6] = idx20;
                    int idx21 = c * (inH * inW) + (i + 2) * inW + j + 1;
                    ints[c * 9 + 7] = idx21;
                    int idx22 = c * (inH * inW) + (i + 2) * inW + j + 2;
                    ints[c * 9 + 8] = idx22;
                }
                Variable get = new GetColumnsFunc(x, ints).toVar();
                vars.add(get);

                Variable dot = new DotFunc(get, W).toVar();
                vars.add(dot);

                int[] ints1 = new int[kernelC];
                for (int c = 0; c < kernelC; c++) {
                    int idx = c * ((inH - 2) * (inW - 2)) + i * (inW - 2) + j;
                    ints1[c] = idx;
                }
                y = new PutColumnsFunc(y, dot, ints1).toVar();
                vars.add(y);
            }
        }
        return y;
    }

    // 逻辑和build一样，只是这个返回的是DoubleMatrix，用于正向计算时用
    public DoubleMatrix calc(DoubleMatrix x) {
        int rr = x.rows;
        int cc = kernelC * (inH - 2) * (inW - 2);

        DoubleMatrix y = DoubleMatrix.zeros(rr, cc);

        for (int i = 0; i < inH - 2; i++) {
            for (int j = 0; j < inW - 2; j++) {
                int[] ints = new int[inC * 3 * 3];
                for (int c = 0; c < inC; c++) {
                    int idx00 = c * (inH * inW) + i * inW + j;
                    ints[c * 9] = idx00;
                    int idx01 = c * (inH * inW) + i * inW + j + 1;
                    ints[c * 9 + 1] = idx01;
                    int idx02 = c * (inH * inW) + i * inW + j + 2;
                    ints[c * 9 + 2] = idx02;
                    int idx10 = c * (inH * inW) + (i + 1) * inW + j;
                    ints[c * 9 + 3] = idx10;
                    int idx11 = c * (inH * inW) + (i + 1) * inW + j + 1;
                    ints[c * 9 + 4] = idx11;
                    int idx12 = c * (inH * inW) + (i + 1) * inW + j + 2;
                    ints[c * 9 + 5] = idx12;
                    int idx20 = c * (inH * inW) + (i + 2) * inW + j;
                    ints[c * 9 + 6] = idx20;
                    int idx21 = c * (inH * inW) + (i + 2) * inW + j + 1;
                    ints[c * 9 + 7] = idx21;
                    int idx22 = c * (inH * inW) + (i + 2) * inW + j + 2;
                    ints[c * 9 + 8] = idx22;
                }

                DoubleMatrix get = MatrixFunction.getColumns(x, ints);

                DoubleMatrix dot = get.mmul(W.getValue());

                int[] ints1 = new int[kernelC];
                for (int c = 0; c < kernelC; c++) {
                    int idx = c * ((inH - 2) * (inW - 2)) + i * (inW - 2) + j;
                    ints1[c] = idx;
                }
                y = MatrixFunction.putColumns(y, dot, ints1);
            }
        }
        return y;
    }



    @Override
    public Variable[] getParams() {
        return new Variable[]{W};
    }

    @Override
    public Variable[] getVariables() {
        return vars.toArray(new Variable[0]);
    }
}

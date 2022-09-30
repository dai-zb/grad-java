package com.dzb.math.auto2.func;

import com.dzb.math.auto2.Func2Var;
import com.dzb.math.auto2.Variable;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-04 14:28
 */
public class Conv3DFunc extends Func2Var {
    private final int inB;
    private final int inC;
    private final int inH;
    private final int inW;
    private final int kernelC;

    public Conv3DFunc(Variable param0, Variable param1,
                      int inB, int inC, int inH, int inW) {
        super(param0, param1);
        // 输入图片的尺寸 C, H(行), W(列)
        this.inB = inB;
        this.inC = inC;
        this.inH = inH;
        this.inW = inW;

        // 卷积核的通道 (3x3的卷积核)
        this.kernelC = param1.getValue().columns;
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix x = getParam0().getValue();
        DoubleMatrix w = getParam1().getValue();

        return conv(x, w, inB, inC, inH, inW, kernelC);
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix x = getParam0().getValue();
        DoubleMatrix w = getParam1().getValue();

        DoubleMatrix xGrad = DoubleMatrix.zeros(x.rows, x.columns);

        for (int i = 0; i < inH - 2; i++) {
            for (int j = 0; j < inW - 2; j++) {
                int[] ints = getIdxs(i, j, inC, inH, inW);

                DoubleMatrix xxGrad = MatrixFunction.getColumns(xGrad, ints);

                int[] ints1 = getLocs(i, j, inH, inW, kernelC);

                DoubleMatrix yyGrad = MatrixFunction.getColumns(grad, ints1);

                xxGrad.addi(yyGrad.mmul(w.transpose()));

                xGrad = MatrixFunction.putColumns(xGrad, xxGrad, ints);
            }
        }
        return xGrad;
    }

    @Override
    public DoubleMatrix partial1(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix x = getParam0().getValue();
        DoubleMatrix w = getParam1().getValue();

        DoubleMatrix wGrad = DoubleMatrix.zeros(w.rows, w.columns);

        for (int i = 0; i < inH - 2; i++) {
            for (int j = 0; j < inW - 2; j++) {
                int[] ints = getIdxs(i, j, inC, inH, inW);

                DoubleMatrix xx = MatrixFunction.getColumns(x, ints);

                int[] ints1 = getLocs(i, j, inH, inW, kernelC);

                DoubleMatrix yyGrad = MatrixFunction.getColumns(grad, ints1);

                wGrad.addi(xx.transpose().mmul(yyGrad));
            }
        }
        return wGrad;
    }

    public static DoubleMatrix conv(DoubleMatrix x, DoubleMatrix w,
                                    int inB, int inC, int inH, int inW, int kernelC) {

        int cc = kernelC * (inH - 2) * (inW - 2);

        DoubleMatrix y = DoubleMatrix.zeros(inB, cc);

        for (int i = 0; i < inH - 2; i++) {
            for (int j = 0; j < inW - 2; j++) {
                int[] ints = getIdxs(i, j, inC, inH, inW);

                DoubleMatrix get = MatrixFunction.getColumns(x, ints);

                DoubleMatrix dot = get.mmul(w);

                int[] ints1 = getLocs(i, j, inH, inW, kernelC);

                y = MatrixFunction.putColumns(y, dot, ints1);
            }
        }
        return y;
    }

    // 根据 i j 确定卷积核计算的位置
    private static int[] getIdxs(int i, int j, int inC, int inH, int inW) {
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
        return ints;
    }

    // 根据 i j 确定卷积核计算后结果存放的位置
    private static int[] getLocs(int i, int j, int inH, int inW, int kernelC) {
        int[] ints = new int[kernelC];
        for (int c = 0; c < kernelC; c++) {
            int idx = c * ((inH - 2) * (inW - 2)) + i * (inW - 2) + j;
            ints[c] = idx;
        }
        return ints;
    }
}

package com.dzb.math.auto2.func;

import com.dzb.math.auto2.Func1Var;
import com.dzb.math.auto2.Variable;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-06 14:23
 */
// 2x2 的 Max Pooling
public class MaxPoolingFunc extends Func1Var {
    private final int inB;
    private final int inC;
    private final int inH;
    private final int inW;

    private final int inHHalf;
    private final int inWHalf;

    private final int[][][][] maxIdx;

    public MaxPoolingFunc(Variable param0,
                          int inB, int inC, int inH, int inW) {
        super(param0);
        this.inB = inB;
        this.inC = inC;
        this.inH = inH;
        this.inW = inW;
        this.inHHalf = inH / 2;
        this.inWHalf = inW / 2;
        this.maxIdx = new int[inC][inHHalf][inWHalf][inB];
    }

    @Override
    public DoubleMatrix doCalc() {
        return maxPooling(getParam0().getValue(), inB, inC, inH, inW, maxIdx);
    }

    public static DoubleMatrix maxPooling(DoubleMatrix x,
                                          int inB, int inC, int inH, int inW,
                                          int[][][][] maxIdx) {
        int inHHalf = inH / 2;
        int inWHalf = inW / 2;

        DoubleMatrix y = DoubleMatrix.zeros(inB, inC * inHHalf * inWHalf);

        for (int c = 0; c < inC; c++) {
            for (int i = 0; i < inHHalf; i++) {
                for (int j = 0; j < inWHalf; j++) {

                    int idx00 = c * (inH * inW) + (2 * i) * inW + (2 * j);
                    int idx01 = c * (inH * inW) + (2 * i) * inW + ((2 * j) + 1);
                    int idx10 = c * (inH * inW) + ((2 * i) + 1) * inW + (2 * j);
                    int idx11 = c * (inH * inW) + ((2 * i) + 1) * inW + ((2 * j) + 1);

                    int[] ints = new int[]{idx00, idx01, idx10, idx11};
                    DoubleMatrix get = MatrixFunction.getColumns(x, ints);

                    DoubleMatrix maxPool = get.rowMaxs();
                    maxIdx[c][i][j] = get.rowArgmaxs();

                    int idx = c * inWHalf * inWHalf + i * inWHalf + j;

                    y.putColumn(idx, maxPool.getColumn(0));
                }
            }
        }
        return y;
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix xGrad = DoubleMatrix.zeros(inB, inC * inH * inW);

        for (int c = 0; c < inC; c++) {
            for (int i = 0; i < inHHalf; i++) {
                for (int j = 0; j < inWHalf; j++) {

                    int idx00 = c * (inH * inW) + (2 * i) * inW + (2 * j);
                    int idx01 = c * (inH * inW) + (2 * i) * inW + ((2 * j) + 1);
                    int idx10 = c * (inH * inW) + ((2 * i) + 1) * inW + (2 * j);
                    int idx11 = c * (inH * inW) + ((2 * i) + 1) * inW + ((2 * j) + 1);

                    int[] idxs = new int[]{idx00, idx01, idx10, idx11};

                    int idx0 = c * (inHHalf * inWHalf) + i * inWHalf + j;

                    int[] maxIdx = this.maxIdx[c][i][j];

                    DoubleMatrix column = grad.getColumn(idx0);

                    for (int k = 0; k < maxIdx.length; k++) {
                        xGrad.put(k, idxs[maxIdx[k]], column.get(k));
                    }
                }
            }
        }
        return xGrad;
    }
}

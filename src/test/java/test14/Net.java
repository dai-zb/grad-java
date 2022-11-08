package test14;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.Conv3DFunc;
import com.dzb.math.auto2.func.MaxPoolingFunc;
import com.dzb.math.auto2.func.func.SigmoidFunc;
import com.dzb.math.auto2.lossFunc.SoftmaxCrossEntropyLossFunc;
import com.dzb.math.auto3.module.Linear;
import com.dzb.math.auto3.optimizer.Sgd;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

public class Net {
    private final Sgd sgd;

    private final Constant X;
    private final Constant Y_BAR;
    private final Variable Z6;
    private final Variable l;

    public Net(int batchSize, int xLen, int hideNum, int yLen, double lr) { // m n l k
        sgd = new Sgd(lr);

        X = new Constant(DoubleMatrix.zeros(batchSize, xLen));
        sgd.add(null, X);

        DoubleMatrix w = DoubleMatrix.randn(1 * 3 * 3, 4);
        w.muli(1);
        Variable W = new Variable(w);

        Y_BAR = new Constant(DoubleMatrix.zeros(batchSize, yLen));
        sgd.add(null, Y_BAR);

        // 卷积层 ==========================================
        Variable Z1 = new Conv3DFunc(X, W, batchSize, 1, 28, 28).toVar();
        sgd.add(W, X);
        sgd.add(null, Z1);

        // Sigmoid层 ==========================================
        Variable Z2 = new SigmoidFunc(Z1).toVar();
        sgd.add(null, Z2);

        // 池化层     ==========================================
        Variable Z3 = new MaxPoolingFunc(Z2, batchSize, 4, 26, 26).toVar();
        sgd.add(null, Z3);

        // 线性层1 ==========================================
        Linear linear1 = new Linear(batchSize, 4 * 13 * 13, hideNum, 0.01);
        Variable Z4 = linear1.build(Z3);
        sgd.add(linear1.getParams(), linear1.getVariables());
        sgd.add(null, Z4);

        // Sigmoid层 ==========================================
        Variable Z5 = new SigmoidFunc(Z4).toVar();
        sgd.add(null, Z5);

        // 线性层2 ==========================================
        Linear linear2 = new Linear(batchSize, hideNum, yLen, 0.01);
        this.Z6 = linear2.build(Z5);
        sgd.add(linear2.getParams(), linear2.getVariables());
        sgd.add(null, this.Z6);

        // 损失函数 ========================================
        l = new SoftmaxCrossEntropyLossFunc(this.Z6, Y_BAR).toVar();
        sgd.add(null, l);
    }

    public void train(DoubleMatrix x, DoubleMatrix y, int[] yInt) {
        X.setValue(x);
        Y_BAR.setValue(y);

        // 正向传播
        l.forward();

        double loss = l.getValue().scalar();
        DoubleMatrix Y = MatrixFunction.softmax1(Z6.getValue());
        double accuracy = accuracy(Y, yInt);
        System.out.println("loss: " + loss + "     train accuracy: " + accuracy);

        // 反向传播
        l.backward();

        sgd.step();
        sgd.clear();
    }

    public void test(DoubleMatrix x, DoubleMatrix y, int[] yInt) {
        X.setValue(x);
        Y_BAR.setValue(y);

        // 正向传播
        l.forward();

        double loss = l.getValue().scalar();
        DoubleMatrix Y = MatrixFunction.softmax1(Z6.getValue());
        double accuracy = accuracy(Y, yInt);
        System.out.println("loss: " + loss + "     accuracy: " + accuracy);
        sgd.clear();
    }

    private double accuracy(DoubleMatrix y, int[] yInt) {
        int rows = y.rows;
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            double[] data = y.getRow(i).data;
            int idx = 0;
            double max = data[0];
            for (int j = 1; j < data.length; j++) {
                if (data[j] > max) {
                    idx = j;
                    max = data[j];
                }
            }
            if (idx == yInt[i]) {
                sum++;
            }
        }
        return sum / rows;
    }
}

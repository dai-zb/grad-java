package math9;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.func.ReLUFunc;
import com.dzb.math.auto2.lossFunc.SoftmaxCrossEntropyLossFunc;
import com.dzb.math.auto3.module.Linear;
import com.dzb.math.auto3.optimizer.Sgd;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

public class Net {
    private final Sgd sgd;

    private final Linear linear1;
    private final Linear linear2;

    private final Constant X;
    private final Constant Y_BAR;
    private final Variable Z3;
    private final Variable l;

    public Net(int batchSize, int xLen, int hideNum, int yLen, double lr) { // m n l k
        sgd = new Sgd(lr);

        X = new Constant(DoubleMatrix.zeros(batchSize, xLen));
        sgd.add(null, X);

        Y_BAR = new Constant(DoubleMatrix.zeros(batchSize, yLen));
        sgd.add(null, Y_BAR);

        // 第一层 ==========================================
        linear1 = new Linear(batchSize, xLen, hideNum, 0.01);
        Variable Z1 = linear1.build(X);
        sgd.add(linear1.getParams(), linear1.getVariables());
        sgd.add(null, Z1);

        // ReLU层 ==========================================
        Variable Z2 = new ReLUFunc(Z1).toVar();
        sgd.add(null, Z2);

        // 第二层 ==========================================
        linear2 = new Linear(batchSize, hideNum, yLen, 0.01);
        Z3 = linear2.build(Z2);
        sgd.add(linear2.getParams(), linear2.getVariables());
        sgd.add(null, Z3);

        // 损失函数 ========================================
        l = new SoftmaxCrossEntropyLossFunc(Z3, Y_BAR).toVar();
        sgd.add(null, l);
    }

    public void train(DoubleMatrix x, DoubleMatrix y, int[] yInt) {
        X.setValue(x);
        Y_BAR.setValue(y);

        // 正向传播
        l.forward();

        double loss = l.getValue().scalar();
        DoubleMatrix Y = MatrixFunction.softmax1(Z3.getValue());
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

        DoubleMatrix Y = MatrixFunction.softmax1(Z3.getValue());
        double accuracy = accuracy(Y, yInt);

        System.out.println("test accuracy: " + accuracy);

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

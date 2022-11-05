package math11;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.func.SigmoidFunc;
import com.dzb.math.auto2.lossFunc.SoftmaxCrossEntropyLossFunc;
import com.dzb.math.auto3.module.Conv3D;
import com.dzb.math.auto3.module.Linear;
import com.dzb.math.auto3.module.MaxPooling2D4;
import com.dzb.math.auto3.optimizer.Sgd;
import org.jblas.DoubleMatrix;

public class Net {
    private final Sgd sgd;

    private final Conv3D conv3D;
    private final MaxPooling2D4 maxPooling2D4;
    private final Linear linear1;
    private final Linear linear2;

    private final Constant X;
    private final Constant Y_BAR;
    private final Variable Z6;
    private final Variable l;

    public Net(int batchSize, int xLen, int hideNum, int yLen, double lr) { // m n l k
        sgd = new Sgd(lr);

        X = new Constant(DoubleMatrix.zeros(batchSize, xLen));
        sgd.add(null, X);

        Y_BAR = new Constant(DoubleMatrix.zeros(batchSize, yLen));
        sgd.add(null, Y_BAR);

        // 卷积层 ==========================================
        conv3D = new Conv3D(batchSize, 1, 28, 28, 4, 1);
        Variable Z1 = conv3D.build(X);
        sgd.add(conv3D.getParams(), conv3D.getVariables());
        sgd.add(null, Z1);

        // Sigmoid层 ==========================================
        Variable Z2 = new SigmoidFunc(Z1).toVar();
        sgd.add(null, Z2);

        // 池化层     ==========================================
        maxPooling2D4 = new MaxPooling2D4(batchSize, 4, 26, 26);
        Variable Z3 = maxPooling2D4.build(Z2);
        sgd.add(maxPooling2D4.getParams(), maxPooling2D4.getVariables());
        sgd.add(null, Z3);

        // 线性层1 ==========================================
        linear1 = new Linear(batchSize, 4 * 13 * 13, hideNum, 0.01);
        Variable Z4 = linear1.build(Z3);
        sgd.add(linear1.getParams(), linear1.getVariables());
        sgd.add(null, Z4);

        // Sigmoid层 ==========================================
        Variable Z5 = new SigmoidFunc(Z4).toVar();
        sgd.add(null, Z5);

        // 线性层2 ==========================================
        linear2 = new Linear(batchSize, hideNum, yLen, 0.01);
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
//        DoubleMatrix Y = MatrixFunction.softmax1(Z6.getValue());
//        double accuracy = accuracy(Y, yInt);
//        System.out.println("loss: " + loss + "     train accuracy: " + accuracy);
        System.out.println("loss: " + loss);

        // 反向传播
        l.backward();

        sgd.step();
        sgd.clear();
    }

    public void test(DoubleMatrix x, int[] yInt) {
//        DoubleMatrix z = linear1.calc(x);
//        z = MatrixFunction.relu(z);
//        z = linear2.calc(z);
//        z = MatrixFunction.softmax1(z);
//
//        double accuracy = accuracy(z, yInt);
//        System.out.println("test accuracy: " + accuracy);
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

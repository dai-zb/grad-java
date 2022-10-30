package math5;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.AddFunc;
import com.dzb.math.auto2.func.matrix.DotFunc;
import com.dzb.math.auto2.func.func.ReLUFunc;
import com.dzb.math.auto2.lossFunc.SoftmaxCrossEntropyLossFunc;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

public class Net {
    private Constant I;

    private Variable W1;
    private Variable B1;

    private Variable W2;
    private Variable B2;

    public Net(int batchSize, int xLen, int hideNum, int yLen) { // m n l k

        DoubleMatrix i = DoubleMatrix.ones(batchSize, 1);
        I = new Constant(i);

        // 第一层 ==========================================
        DoubleMatrix w1 = DoubleMatrix.randn(xLen, hideNum);
        w1.muli(0.01);
        W1 = new Variable(w1);

        DoubleMatrix b1 = DoubleMatrix.zeros(1, hideNum);
        B1 = new Variable(b1);

        // 第一层 ==========================================
        DoubleMatrix w2 = DoubleMatrix.randn(hideNum, yLen);
        w2.muli(0.01);
        W2 = new Variable(w2);

        DoubleMatrix b2 = DoubleMatrix.zeros(1, yLen);
        B2 = new Variable(b2);

    }

    public void train(DoubleMatrix x, DoubleMatrix y, int[] yInt, double lr) {
        Constant X = new Constant(x);

        Constant Y_BAR = new Constant(y);

        // 第一层 ==========================================
        DotFunc dotFunc = new DotFunc(X, W1);
        Variable U1 = new Variable(dotFunc);

        DotFunc dotFunc1 = new DotFunc(I, B1);
        Variable V1 = new Variable(dotFunc1);

        AddFunc addFunc = new AddFunc(U1, V1);
        Variable Z1 = new Variable(addFunc);

        // ReLU层 ==========================================
        ReLUFunc reLUFunc = new ReLUFunc(Z1);
        Variable Z2 = new Variable(reLUFunc);

        // 第二层 ==========================================
        DotFunc dotFunc2 = new DotFunc(Z2, W2);
        Variable U2 = new Variable(dotFunc2);

        DotFunc dotFunc3 = new DotFunc(I, B2);
        Variable V2 = new Variable(dotFunc3);

        AddFunc addFunc2 = new AddFunc(U2, V2);
        Variable Z3 = new Variable(addFunc2);

        // 损失函数 ==========================================
        SoftmaxCrossEntropyLossFunc softmaxCrossEntropyLossFunc = new SoftmaxCrossEntropyLossFunc(Z3, Y_BAR);
        Variable l = new Variable(softmaxCrossEntropyLossFunc);

        // 正向传播
        l.forward();

        double loss = l.getValue().scalar();

        DoubleMatrix Y = MatrixFunction.softmax1(Z3.getValue());
        double accuracy = accuracy(Y, yInt);

        System.out.println("loss: " + loss + "     train accuracy: " + accuracy);

        // 反向传播
        l.backward();

        // 使用inplace方法，减少对象的创建
        // 因为grad马上就被清除了，所以这里使用muli
        // 使用addi 所以直接就把value更新了
        DoubleMatrix newW1 = W1.getValue().addi(W1.getGrad().muli(-1 * lr));
        W1.zeroGrad();
        // W1.setValue(newW1);

        DoubleMatrix newB1 = B1.getValue().addi(B1.getGrad().muli(-1 * lr));
        B1.zeroGrad();
        // B1.setValue(newB1);

        DoubleMatrix newW2 = W2.getValue().addi(W2.getGrad().muli(-1 * lr));
        W2.zeroGrad();
        // W2.setValue(newW2);

        DoubleMatrix newB2 = B2.getValue().addi(B2.getGrad().muli(-1 * lr));
        B2.zeroGrad();
        // B2.setValue(newB2);

        U1.zeroGrad();
        U1.clear();

        V1.zeroGrad();
        V1.clear();

        Z1.zeroGrad();
        Z1.clear();

        Z2.zeroGrad();
        Z2.clear();

        U2.zeroGrad();
        U2.clear();

        V2.zeroGrad();
        V2.clear();

        Z3.zeroGrad();
        Z3.clear();

        l.zeroGrad();
        l.clear();
    }

    public void test(DoubleMatrix x, int[] yInt) {
        DoubleMatrix i = I.getValue();

        DoubleMatrix w1 = W1.getValue();
        DoubleMatrix b1 = B1.getValue();

        DoubleMatrix z = x.mmul(w1).add(i.mmul(b1));

        z = MatrixFunction.relu(z);

        DoubleMatrix w2 = W2.getValue();
        DoubleMatrix b2 = B2.getValue();

        z = z.mmul(w2).add(i.mmul(b2));

        z = MatrixFunction.softmax1(z);

        double accuracy = accuracy(z, yInt);
        System.out.println("test accuracy: " + accuracy);
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

package math4;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.AddFunc;
import com.dzb.math.auto2.func.matrix.DotFunc;
import com.dzb.math.auto2.lossFunc.SoftmaxCrossEntropyLossFunc;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

public class Net {
    private Variable W;
    private Constant I;
    private Variable B;

    public Net(int batchSize, int xLen, int yLen) { // m n k
        DoubleMatrix w = DoubleMatrix.randn(xLen, yLen);
        w.muli(0.01);
        //System.out.println(w.toString("%.4f", "[", "]", ", ", "\n"));
        W = new Variable(w);

        DoubleMatrix i = DoubleMatrix.ones(batchSize, 1);
        I = new Constant(i);

        DoubleMatrix b = DoubleMatrix.zeros(1, yLen);
        B = new Variable(b);
    }

    public void train(DoubleMatrix x, DoubleMatrix y, int[] yInt, double lr) {
        Constant X = new Constant(x);
        Constant Y_bar = new Constant(y);
        DotFunc dotFunc = new DotFunc(X, W);
        Variable U = new Variable(dotFunc);

        DotFunc dotFunc1 = new DotFunc(I, B);
        Variable V = new Variable(dotFunc1);

        AddFunc addFunc = new AddFunc(U, V);
        Variable Z = new Variable(addFunc);

        SoftmaxCrossEntropyLossFunc softmaxCrossEntropyLossFunc = new SoftmaxCrossEntropyLossFunc(Z, Y_bar);
        Variable l = new Variable(softmaxCrossEntropyLossFunc);

        l.forward();

        double loss = l.getValue().scalar();

        DoubleMatrix Y = MatrixFunction.softmax1(Z.getValue());
        double accuracy = accuracy(Y, yInt);

        System.out.println("loss: " + loss + "     train accuracy: " + accuracy);

        l.backward();

        DoubleMatrix newW = W.getValue().add(W.getGrad().mul(-1 * lr));
        W.setValue(newW);
        W.zeroGrad();

        DoubleMatrix newB = B.getValue().add(B.getGrad().mul(-1 * lr));
        B.setValue(newB);
        B.zeroGrad();

        U.clear();
        U.zeroGrad();

        V.clear();
        V.zeroGrad();

        Z.clear();
        Z.zeroGrad();

        l.clear();
        l.zeroGrad();
    }

    public void test(DoubleMatrix x, int[] yInt) {
        DoubleMatrix w = W.getValue();
        DoubleMatrix b = B.getValue();
        DoubleMatrix i = I.getValue();

        DoubleMatrix y = x.mmul(w).add(i.mmul(b));
        y = MatrixFunction.softmax1(y);
        double accuracy = accuracy(y, yInt);
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

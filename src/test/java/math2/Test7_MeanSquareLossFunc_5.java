package math2;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.AddFunc;
import com.dzb.math.auto2.func.matrix.DotFunc;
import com.dzb.math.auto2.lossFunc.MeanSquareLossFunc;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-21 16:01
 */
public class Test7_MeanSquareLossFunc_5 {

    @Test
    public void test5() {
        double[][] d1 = new double[][]{
                {4, 2, 3},
                {1, 4, 3},
                {1, 2, 5}};
        double[][] d2 = new double[][]{
                {3, 2, 5},
                {1, 9, 3},
                {7, 2, 1}};
        double[][] d3 = new double[][]{
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}};
        double[][] d4 = new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {1, 0, 0}};

        Variable X = new Variable(new DoubleMatrix(d1));
        Variable W = new Variable(new DoubleMatrix(d2));
        Variable B = new Variable(new DoubleMatrix(d3));
        Constant ZZ = new Constant(new DoubleMatrix(d4));

        DotFunc dotFunc = new DotFunc(X, W);
        Variable Y = new Variable(dotFunc);

        AddFunc addFunc = new AddFunc(Y, B);
        Variable Z = new Variable(addFunc);

        MeanSquareLossFunc loss = new MeanSquareLossFunc(Z,ZZ);
        Variable l = new Variable(loss);
        // [3019.3333]

        l.forward();
        DoubleMatrix value = l.getValue();
        System.out.println(value.toString("%.4f", "[", "]", ", ", "\n"));

        l.backward();

        DoubleMatrix xGrad = X.getGrad();
        System.out.println("X Grad");
        System.out.println(xGrad.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [212.6667, 275.3333, 226.0000
        181.3333, 322.6667, 202.6667
        176.6667, 240.6667, 238.0000]
         */

        DoubleMatrix wGrad = W.getGrad();
        System.out.println("W Grad");
        System.out.println(wGrad.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [138.6667, 134.6667, 104.6667
        174.6667, 200.0000, 116.0000
        259.3333, 252.0000, 156.6667]
         */

        DoubleMatrix bGrad = B.getGrad();
        System.out.println("B Grad");
        System.out.println(bGrad.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [23.3333, 21.3333, 20.0000
        18.6667, 29.3333, 13.3333
        26.6667, 20.0000, 11.3333]
         */

        DoubleMatrix yGrad = Y.getGrad();
        System.out.println("Y Grad");
        System.out.println(yGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix zGrad = Z.getGrad();
        System.out.println("Z Grad");
        System.out.println(zGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix lGrad = l.getGrad();
        System.out.println("l Grad");
        System.out.println(lGrad.toString("%.4f", "[", "]", ", ", "\n"));

        System.out.println();
    }
}

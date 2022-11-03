package math7;

import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.toScalar.SumFunc;
import com.dzb.math.auto2.func.GetColumnsFunc;
import com.dzb.math.auto3.func.MaxPooling4Func;
import com.dzb.math.auto2.func.PutColumnsFunc;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 13:30
 */
public class Test1 {
    @Test
    public void test1() {
        double[][] x = {
                {-6.0000, -5.5000, -5.0000, -4.5000, -4.0000},
                {-3.5000, -3.0000, -2.5000, -2.0000, -1.5000},
                {-1.0000, -0.5000, 0.0000, 0.5000, 1.0000},
                {1.5000, 2.0000, 2.5000, 3.0000, 3.5000},
                {4.0000, 4.5000, 5.0000, 5.5000, 6.0000}
        };
        Variable X = new Variable(new DoubleMatrix(x));
        int[] idx = new int[]{0, 2, 3};
        GetColumnsFunc func = new GetColumnsFunc(X, idx);
        Variable Y = new Variable(func);
        Y.forward();

        System.out.println(Y.getValue().toString("%.4f", "[", "]", ", ", "\n"));
    }

    @Test
    public void test2() {
        double[][] x = {
                {-6.0000, -5.5000, -5.0000, -4.5000, -4.0000},
                {-3.5000, -3.0000, -2.5000, -2.0000, -1.5000},
                {-1.0000, -0.5000, 0.0000, 0.5000, 1.0000},
                {1.5000, 2.0000, 2.5000, 3.0000, 3.5000},
                {4.0000, 4.5000, 5.0000, 5.5000, 6.0000}
        };
        Variable X = new Variable(new DoubleMatrix(x));
        int[] idx = new int[]{0, 2, 3};
        GetColumnsFunc func = new GetColumnsFunc(X, idx);
        Variable Y = new Variable(func);
        SumFunc sumFunc = new SumFunc(Y);

        Variable Z = new Variable(sumFunc);
        Z.forward();

        System.out.println(Y.getValue().toString("%.4f", "[", "]", ", ", "\n"));

        Z.backward();

        System.out.println(Y.getGrad().toString("%.4f", "[", "]", ", ", "\n"));

        System.out.println(X.getGrad().toString("%.4f", "[", "]", ", ", "\n"));
    }

    @Test
    public void test3() {
        Variable C = new Variable(DoubleMatrix.zeros(5, 5));

        double[][] x = {
                {-6.0000, -5.5000, -5.0000, -4.5000, -4.0000},
                {-3.5000, -3.0000, -2.5000, -2.0000, -1.5000},
                {-1.0000, -0.5000, 0.0000, 0.5000, 1.0000},
                {1.5000, 2.0000, 2.5000, 3.0000, 3.5000},
                {4.0000, 4.5000, 5.0000, 5.5000, 6.0000}
        };
        Variable X = new Variable(new DoubleMatrix(x));

        PutColumnsFunc func = new PutColumnsFunc(C, X, new int[]{2, 4});
        Variable Y = new Variable(func);

        SumFunc sumFunc = new SumFunc(Y);
        Variable Z = new Variable(sumFunc);

        Z.forward();

        System.out.println(Y.getValue().toString("%.4f", "[", "]", ", ", "\n"));

        Z.backward();

        System.out.println(Y.getGrad().toString("%.4f", "[", "]", ", ", "\n"));

        System.out.println(C.getGrad().toString("%.4f", "[", "]", ", ", "\n"));
        System.out.println(X.getGrad().toString("%.4f", "[", "]", ", ", "\n"));
    }

    @Test
    public void test4() {
        double[][] x = {
                {-6.0000, -5.5000, -5.0000, -4.5000, -4.0000},
                {-3.5000, -3.0000, -2.5000, -2.0000, -1.5000},
                {-1.0000, -0.5000, 10.0000, 0.5000, 1.0000},
                {11.5000, 2.0000, 2.5000, 3.0000, 3.5000},
                {14.0000, 4.5000, 5.0000, 5.5000, 6.0000}
        };
        Variable X = new Variable(new DoubleMatrix(x));

        MaxPooling4Func func = new MaxPooling4Func(X);
        Variable Y = new Variable(func);

        SumFunc sumFunc = new SumFunc(Y);
        Variable Z = new Variable(sumFunc);

        Z.forward();

        System.out.println(Y.getValue().toString("%.4f", "[", "]", ", ", "\n"));

        Z.backward();

        System.out.println(Y.getGrad().toString("%.4f", "[", "]", ", ", "\n"));

        System.out.println(X.getGrad().toString("%.4f", "[", "]", ", ", "\n"));
    }
}

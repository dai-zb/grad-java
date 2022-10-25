package math2;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.AddFunc;
import com.dzb.math.auto2.func.matrix.DotFunc;
import com.dzb.math.auto2.func.toScalar.MeanSquareFunc;
import com.dzb.math.auto2.func.func.SigmoidFunc;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-23 17:14
 */
public class Test6_Constant_4 {

    @Test
    public void test4() {
        int m = 10;
        int n = 12;
        int k = 2;
        DoubleMatrix x = DoubleMatrix.randn(m, n);
        DoubleMatrix w = DoubleMatrix.randn(n, k);
        DoubleMatrix a = DoubleMatrix.ones(m, 1);
        DoubleMatrix b = DoubleMatrix.randn(1, k);

        System.out.println(x.toString("%.4f", "[\n[", "]\n]", ", ", "],\n["));
        System.out.println(w.toString("%.4f", "[\n[", "]\n]", ", ", "],\n["));
        System.out.println(a.toString("%.4f", "[\n[", "]\n]", ", ", "],\n["));
        System.out.println(b.toString("%.4f", "[\n[", "]\n]", ", ", "],\n["));

        Variable X = new Variable(x);
        Variable W = new Variable(w);
        Constant A = new Constant(a);
        Variable B = new Variable(b);

        DotFunc dotFunc = new DotFunc(X, W);
        DotFunc dotFunc1 = new DotFunc(A, B);
        Variable U = new Variable(dotFunc);
        Variable V = new Variable(dotFunc1);

        AddFunc addFunc = new AddFunc(U, V);
        Variable Y = new Variable(addFunc);

        SigmoidFunc sigmoidFunc = new SigmoidFunc(Y);
        Variable Z = new Variable(sigmoidFunc);

        MeanSquareFunc meanSquareFunc = new MeanSquareFunc(Z);
        Variable l = new Variable(meanSquareFunc);

        l.forward();

        DoubleMatrix uValue = U.getValue();
        System.out.println("U value");
        System.out.println(uValue.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix vValue = V.getValue();
        System.out.println("V value");
        System.out.println(vValue.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix value = l.getValue();
        System.out.println("l");
        System.out.println(value);

        DoubleMatrix zValue = Z.getValue();
        System.out.println("Z value");
        System.out.println(zValue.toString("%.4f", "[", "]", ", ", "\n"));

        l.backward();

        System.out.println("X Grad");
        System.out.println(X.getGrad().toString("%.4f", "[", "]", ", ", "\n"));

        System.out.println("W Grad");
        System.out.println(W.getGrad().toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix yGrad = A.getGrad();
        System.out.println("A Grad");
        System.out.println(yGrad);

        System.out.println("B Grad");
        System.out.println(B.getGrad().toString("%.4f", "[", "]", ", ", "\n"));
    }
}

package math2;

import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.DotFunc;
import com.dzb.math.auto2.func.func.SigmoidFunc;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-24 16:58
 */
public class Test10_scalar {
    // 任何一个函数，只要输出是一个变量，则一定可以进行反向传播
    @Test
    public void test1() {
        double[][] d1 = {{1, 1, 1}};
        double[][] d2 = {
                {2},
                {2},
                {2}
        };
        DoubleMatrix x = new DoubleMatrix(d1);
        Variable X = new Variable(x);

        DoubleMatrix y = new DoubleMatrix(d2);
        Variable Y = new Variable(y);

        DotFunc dotFunc = new DotFunc(X, Y);
        Variable Z = new Variable(dotFunc);

        SigmoidFunc sigmoidFunc = new SigmoidFunc(Z);
        Variable U = new Variable(sigmoidFunc);

        U.forward();

        System.out.println("Z value");
        System.out.println(Z.getValue().toString("%.4f", "[", "]", ", ", "\n"));

        System.out.println("U value");
        System.out.println(U.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        // [0.9975]

        U.backward();

        System.out.println("X grad");
        System.out.println(X.getGrad().toString("%.4f", "[", "]", ", ", "\n"));
        // [0.0049, 0.0049, 0.0049]

        System.out.println("Y grad");
        System.out.println(Y.getGrad().toString("%.4f", "[", "]", ", ", "\n"));

        System.out.println("Z grad");
        System.out.println(Z.getGrad().toString("%.4f", "[", "]", ", ", "\n"));

        System.out.println("U grad");
        System.out.println(U.getGrad().toString("%.4f", "[", "]", ", ", "\n"));

        /* python
        import torch

        x = torch.tensor([[1.,1.,1.]])
        x.requires_grad_(requires_grad=True)
        y = torch.tensor([[2.],[2.],[2.]])
        y.requires_grad_(requires_grad=True)
        z=torch.mm(x,y)
        print(z)
        u=torch.sigmoid(z)
        print(u)

        u.backward()

        print(x.grad)
        print(y.grad)

         */
    }
}

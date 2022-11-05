package test12;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.lossFunc.MeanSquareLossFunc;
import com.dzb.math.auto3.module.Conv3D;
import com.dzb.math.auto3.optimizer.Sgd;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-04 12:33
 */
public class Test1 {
    @Test
    public void test1() {
        double[][] x = new double[][]{
                {1., 2., 3., 4., 5., 6., 7., 8., 9., 10.,
                        11., 12., 13., 14., 15., 16., 17., 18., 19., 20.,
                        21., 22., 23., 24., 25., 26., 27., 28., 29., 30.,
                        31., 32., 33., 34., 35., 36., 37., 38., 39., 40.,
                        41., 42., 43., 44., 45., 46., 47., 48., 49., 50.}};
        // B C H W = 1 2 5 5

        double[][] y = new double[][]{{
                1., 2., 3., 4., 5., 6., 7., 8., 9.,
                10., 11., 12., 13., 14., 15., 16., 17., 18}};
        // B C H W = 1 2 3 3

        Variable X = new Constant(new DoubleMatrix(x));
        Constant Y_BAR = new Constant(new DoubleMatrix(y));

        Conv3D conv3D = new Conv3D(1, 2, 5, 5, 2, 0.0004);
        Variable Y = conv3D.build(X);

        Variable l = new MeanSquareLossFunc(Y, Y_BAR).toVar();

        Sgd sgd = new Sgd(0.000005);

        sgd.add(conv3D.getParams(), conv3D.getVariables());
        sgd.add(null, Y);
        sgd.add(null, l);

        for (int i = 0; i < 1000; i++) {
            // 正向传播
            l.forward();
            double loss = l.getValue().scalar();
            System.out.println(i+"  loss: " + loss);
            // 反向传播
            l.backward();
            sgd.step();
            sgd.clear();
        }
        l.forward();
        System.out.println(Y.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        System.out.println(conv3D.getParams()[0].getValue().toString("%.4f", "[", "]", ", ", "\n"));
    }
}

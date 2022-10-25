package math1;

import com.dzb.math.auto1.Func2;
import com.dzb.math.auto1.Var;
import com.dzb.math.auto1.func.AddFunc;
import com.dzb.math.auto1.func.ExpFunc;
import com.dzb.math.auto1.func.MultiplyFunc;
import com.dzb.math.auto1.func.PowFunc;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-04-26 12:38
 */
public class Test3_func {
    @Test
    public void test1() {
        Var x = new Var("x", 12.);
        Var y = new Var("y", 11.);
        Var z = new Var("z", 10.);

        Func2 func = new AddFunc(x, y);
        Func2 func1 = new MultiplyFunc(func, z);

        Var f = new Var("f", func1);
        System.out.println(f);

        System.out.println(f.partial("x"));
    }

    @Test
    public void test2() {
        Var x = new Var("x", 12.);
        Var y = new Var("y", 11.);
        Var z = new Var("z", 10.);

        Func2 func1 = new AddFunc(x, y);
        Func2 func2 = new AddFunc(x, z);
        Func2 func = new MultiplyFunc(func1, func2);

        Var f = new Var("f", func);
        /*
        506.0
        f =
            [[(x = 12.0) + (y = 11.0)] * [(x = 12.0) + (z = 10.0)]]
        45.0
         */

        // 必须要正向计算一下
        Double calc = f.calc();
        System.out.println(calc);

        System.out.println(f);

        System.out.println(f.partial("x"));
    }

    @Test
    public void test3() {
        Var x = new Var("x", 1.5);

        Var w = new Var("w", 12.);
        Var b = new Var("b", -5.);

        MultiplyFunc func = new MultiplyFunc(w, x);
        AddFunc func1 = new AddFunc(func , b);
        ExpFunc func2 = new ExpFunc(func1);

        System.out.println(func2);

        Double calc = func2.calc();
        System.out.println(calc);

        Double partial = func2.partial("x");
        System.out.println(partial);
        /*
        exp[[[(w = 12.0) * (x = 1.5)] + (b = -5.0)]]
        442413.3920089205
        5308960.704107046
         */
    }

    @Test
    public void test4() {
        Var x = new Var("x", 1.5);

        Var y = new Var("y", 12.);
        PowFunc func = new PowFunc(x, 3);
        AddFunc func2 = new AddFunc(func, y);

        System.out.println(func2);

        Double calc = func2.calc();
        System.out.println(calc);

        Double partial = func2.partial("x");
        System.out.println(partial);
        /*
        [pow_3.0[(x = 1.5)] + (y = 12.0)]
        15.375
        6.75
         */
    }
}

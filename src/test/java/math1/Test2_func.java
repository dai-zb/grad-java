package math1;

import com.dzb.math.auto1.Var;
import com.dzb.math.auto1.func.AddFunc;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-04-26 10:06
 */
public class Test2_func {
    @Test
    public void test1() {
        Var x = new Var("x", 12.);

        Var y = new Var("y", 11.);

        Var z = new Var("z", 10.);

        AddFunc func = new AddFunc(x, y);
        AddFunc func1 = new AddFunc(func, z);
        func1.calc();

        System.out.println(func);
        System.out.println(func.getValue());

        System.out.println(func1);
        System.out.println(func1.getValue());
    }

    @Test
    public void test2() {
        Var x = new Var("x", 12.);
        Var y = new Var("y", 11.);
        Var z = new Var("z", 10.);

        AddFunc func = new AddFunc(x, y);
        AddFunc func1 = new AddFunc(func, z);
        func1.calc();

        System.out.println(func1);

        Var var = new Var("f", func1);
        System.out.println(var);
        /*
        [[(x = 12.0) + (y = 11.0)] + (z = 10.0)]
        f =
            [[(x = 12.0) + (y = 11.0)] + (z = 10.0)]
         */
    }

    @Test
    public void test3() {
        Var x = new Var("x", 12.);
        Var y = new Var("y", 11.);
        Var z = new Var("z", 10.);

        AddFunc func = new AddFunc(x, y);
        AddFunc func1 = new AddFunc(func, z);

        Var f = new Var("f", func1);
        System.out.println(f);

        System.out.println(f.partial("x"));
    }
}

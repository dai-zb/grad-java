package math1;

import com.dzb.math.auto1.Var;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-04-26 9:53
 */
public class Test1_Var {
    @Test
    public void test1() {
        Var x = new Var("x", 12.);
        System.out.println(x);

        Var y = new Var("y", x);
        System.out.println(y);

        Var z = new Var("z", y);
        System.out.println(z);
    }

    @Test
    public void test2() {
        Var a = new Var("a", 12.);
        Var b = new Var("a", a);
        System.out.println(b);
    }

    @Test
    public void test3() {
        Var x = new Var("x", 12.);
        Var y = new Var("y", x);
        Var z = new Var("z", y);

        System.out.println(z);
        z.calc();

        x.setValue(10.); // 改变了值
        System.out.println(z.getValue()); // 12.0 还是旧的值
        System.out.println(y.getValue()); // 12.0 还是旧的值
        System.out.println(x.getValue()); // 10.0

        z.calc();

        System.out.println(z.getValue()); // 10.0
        System.out.println(y.getValue()); // 10.0
        System.out.println(x.getValue()); // 10.0
    }

    @Test
    public void test4() {
        Var x = new Var("x", 12.);
        Var y = new Var("y", x);
        Var z = new Var("z", y);

        System.out.println(z.contain("a"));  // false

        System.out.println(z.contain("z"));  // true
        System.out.println(z.contain("y"));  // true
        System.out.println(z.contain("x"));  // true

        System.out.println(y.contain("x"));  // true

        System.out.println(x.contain("z"));  // false
    }

    @Test
    public void test5() {
        Var x = new Var("x", 12.);
        Var y = new Var("y", x);
        Var z = new Var("z", y);

        System.out.println(x.partial("x"));
        System.out.println(x.partial("a"));
        System.out.println(y.partial("z"));
        System.out.println(z.partial("x"));
        System.out.println(z.partial("b"));
    }
}

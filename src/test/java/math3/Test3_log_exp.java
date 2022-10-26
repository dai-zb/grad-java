package math3;

import org.junit.Test;

public class Test3_log_exp {
    @Test
    public void test1(){
        System.out.println(Math.log(1E-300));
        System.out.println(Math.log(1E-320));
        System.out.println(Math.log(1E-323));
        System.out.println(Math.log(0));
    }

    @Test
    public void test2(){
        System.out.println(Math.exp(700));
    }
}

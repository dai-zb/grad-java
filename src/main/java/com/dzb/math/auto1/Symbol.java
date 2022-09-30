package com.dzb.math.auto1;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-04-26 10:40
 */
public interface Symbol {

    // 符号名称
    String getName();

    // 符号的值
    Double getValue();

    // 计算并更新值
    Double calc();

    // 判断是否包含这个参数
    boolean contain(String name);

    // 求偏导
    Double partial(String name);
}

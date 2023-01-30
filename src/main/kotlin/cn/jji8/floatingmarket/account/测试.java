package cn.jji8.floatingmarket.account;

import java.io.*;

public class 测试 {
    public static void main(String[] args) {
        Function function = new Function(new File("C:/工作路径/123.js"));
        Variable variable = new Variable();
        variable.setNumberOfItems(10);
        double sss = function.Doublefunction("价格",variable);
        System.out.println(sss);
    }
}

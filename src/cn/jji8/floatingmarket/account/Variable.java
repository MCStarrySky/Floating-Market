package cn.jji8.floatingmarket.account;
/**
 * 用作处理变量
 * */
public class Variable {
    long NumberOfItems;//库存数量
    public Variable setNumberOfItems(long numberOfItems) {
        NumberOfItems = numberOfItems;
        return this;
    }
    public long getNumberOfItems() {
        return NumberOfItems;
    }
}

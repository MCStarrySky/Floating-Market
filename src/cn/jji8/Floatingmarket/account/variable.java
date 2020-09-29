package cn.jji8.Floatingmarket.account;
/**
 * 用作处理变量
 * */
public class variable {
    long NumberOfItems;//库存数量
    public variable setNumberOfItems(long numberOfItems) {
        NumberOfItems = numberOfItems;
        return this;
    }
    public long getNumberOfItems() {
        return NumberOfItems;
    }
}

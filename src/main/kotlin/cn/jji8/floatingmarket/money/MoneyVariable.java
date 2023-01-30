package cn.jji8.floatingmarket.money;
/**
 * 用作处理变量
 * */
public class MoneyVariable {
    double TransactionAmount;//交易金融

    public MoneyVariable setTransactionAmount(double transactionAmount) {
        TransactionAmount = transactionAmount;
        return this;
    }
    public double getTransactionAmount() {
        return TransactionAmount;
    }
}

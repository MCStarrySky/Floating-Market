package cn.jji8.Floatingmarket.money;
/**
 * 用作处理变量
 * */
public class moneyVariable {
    double TransactionAmount;//交易金融

    public moneyVariable setTransactionAmount(double transactionAmount) {
        TransactionAmount = transactionAmount;
        return this;
    }
    public double getTransactionAmount() {
        return TransactionAmount;
    }
}

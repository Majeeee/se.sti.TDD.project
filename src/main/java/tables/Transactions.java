package tables;

public class Transactions {

    private int transactionId;
    private int userId;
    private int bankId;
    private int ATMId;
    private int transactionsType;
    private double amount;
    private String currency;
    private String time;

    @Override
    public String toString() {
        return "Transactions{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", bankId=" + bankId +
                ", ATMId=" + ATMId +
                ", transactionsType=" + transactionsType +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getATMId() {
        return ATMId;
    }

    public void setATMId(int ATMId) {
        this.ATMId = ATMId;
    }

    public int getTransactionsType() {
        return transactionsType;
    }

    public void setTransactionsType(int transactionsType) {
        this.transactionsType = transactionsType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

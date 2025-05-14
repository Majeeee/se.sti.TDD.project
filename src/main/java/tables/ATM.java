package tables;

public class ATM {
    private int ATMId;
    private String adress;
    private int bankId;

    @Override
    public String toString() {
        return "ATM{" +
                "ATMId=" + ATMId +
                ", adress='" + adress + '\'' +
                ", bankId=" + bankId +
                '}';
    }

    public int getATMId() {
        return ATMId;
    }

    public void setATMId(int ATMId) {
        this.ATMId = ATMId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getBankId() { return bankId; }

    public void setBankId(int bankId) { this.bankId = bankId; }
}

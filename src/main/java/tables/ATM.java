package tables;

public class ATM {
    private int ATMId;
    private String adress;

    @Override
    public String toString() {
        return "ATM{" +
                "ATMId=" + ATMId +
                ", adress='" + adress + '\'' +
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
}

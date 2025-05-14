package tables;

public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private int bankId;
    private int ATMId;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", bankId=" + bankId +
                ", ATMId="+ ATMId +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getBankId() {return bankId;}

    public void setBankId(int bankId) {this.bankId = bankId;}

    public int getATMId() {return ATMId;}

    public void setATMId(int ATMId) {this.ATMId = ATMId;}


}
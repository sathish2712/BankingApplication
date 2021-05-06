import java.lang.reflect.Array;
import java.util.*;
public class customer {
    private int custId;
    private int acctNumber;
    private String name;
    private int balance;
    private String passcode;
    private int passcodeAttempt = 0;

    ArrayList<String> tType = new ArrayList<>();
    ArrayList<Integer> amount = new ArrayList<>();
    ArrayList<Integer> finalBal = new ArrayList<>();

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public int getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(int acctNumber) {
        this.acctNumber = acctNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public int getPasscodeAttempt() {
        return passcodeAttempt;
    }

    public void setPasscodeAttempt(int passcodeAttempt) {
        this.passcodeAttempt = passcodeAttempt;
    }
}

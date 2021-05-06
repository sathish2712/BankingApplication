import java.util.*;
public class Banking {
    static Scanner scan = new Scanner(System.in);
    static int customerId = 1000;
    static int accountNumber = 10000001;
    static int initBalance = 10000;
    ArrayList<customer> customerDetails = new ArrayList();
    Banking() {
        customer c1 = new customer();
        c1.setCustId(11);
        c1.setAcctNumber(1101);
        c1.setName("Kumar");
        c1.setBalance(10000);
        c1.setPasscode(encrypt("acdyz"));
        customerDetails.add(c1);

        customer c2 = new customer();
        c2.setCustId(22);
        c2.setAcctNumber(22022);
        c2.setName("Madhu");
        c2.setBalance(20000);
        c2.setPasscode(encrypt("fayjpz"));
        customerDetails.add(c2);
    }
    String encrypt(String e){
        StringBuffer result = new StringBuffer();
        for(int i = 0 ; i < e.length() ; i++){
            if(Character.isUpperCase(e.charAt(i))){
                char ch = (char) (((int) e.charAt(i) +  1 - 65) % 26 + 65);
                result.append(ch);
            }else{
                char ch = (char) (((int) e.charAt(i) + 1 - 97) % 26 + 97);
                result.append(ch);
            }

        }
        return result.toString();
    }
    String decrypt(String d){
        StringBuffer result = new StringBuffer();
        for(int i = 0 ; i < d.length() ; i++){
            char ch = d.charAt(i);
            if(ch >= 'a' && ch <= 'z'){
                ch = (char) (ch - 1);
                if(ch < 'a'){
                    ch = (char) (ch + 'z' - 'a'  +1);
                }
                result.append(ch);
            }else if(ch >= 'A' && ch <= 'Z'){
                ch = (char)(ch - 1);
                if(ch < 'A'){
                    ch = (char) (ch + 'Z' - 'A' + 1);
                }
                result.append(ch);
            }
        }
        return result.toString();
    }
    void getChoice(){
        System.out.println("-----Welcome to banking system-----");
        System.out.println("1.Add Customer\n2.ATM operations");
        Integer userInput = scan.nextInt();
        switch(userInput){
            case 1: {
                System.out.println("Enter your name");
                String name = scan.next();
                System.out.println("Enter your passcode");
                String pass1 = scan.next();
                System.out.println("Re-type your passcode");
                String pass2 = scan.next();
                if(pass1.equals(pass2)){
                    //adding new customer to list
                    customer c = new customer();
                    c.setName(name);
                    c.setPasscode(encrypt(pass1));
                    c.setCustId(customerId++);
                    c.setAcctNumber(accountNumber++);
                    c.setBalance(initBalance);
                    customerDetails.add(c);
                    System.out.println("New Customer added successfully!");

                    //adding details to trans history
                    c.tType.add("Opening");
                    c.amount.add(initBalance);
                    c.finalBal.add(c.getBalance());
                }else{
                    System.out.println("Passcode doesn't match! Please retry!");
                }
                getChoice();
            }
            break;
            case 2 : {
                showAtmDetails();
                getChoice();
            }
        }
    }

    void showAtmDetails(){
        System.out.println("1.ATM Withdrawal\n2.Cash Deposit\n3.Account Transfer\n4.Transaction History\n5.Top N customer details");
        Integer input = scan.nextInt();
        switch (input){
            case 1 : {
                System.out.println("Enter your account number");
                int acct = scan.nextInt();
                System.out.println("Enter your passcode");
                String pass = scan.next();
                for(customer c : customerDetails){
                    if(c.getAcctNumber() == acct && decrypt(c.getPasscode()).equals(pass)){
                        System.out.println("Successfully Authorized!");
                        System.out.println("Enter the amount to be debited");
                        int amt = scan.nextInt();
                        if(c.getBalance() - amt >= 1000){
                            int temp = c.getBalance() - amt;
                            c.setBalance(temp);
                            System.out.println(amt + " amount has been debited from your account!");
                            System.out.println("Your new balance is "+ temp);

                            //adding trans history
                            c.tType.add("ATM withdrawal");
                            c.amount.add(amt);
                            c.finalBal.add(c.getBalance());
                        }else{
                            System.out.println("Required fund not available!");
                        }
                    }
                }
            }
            break;
            case 2: {
                System.out.println("Enter your account number to deposit your cash!");
                int acct = scan.nextInt();
                boolean actNotFount = false;
                for(customer c : customerDetails){
                    if(c.getAcctNumber() == acct){
                        actNotFount = true;
                        System.out.println("Enter the amount to deposit");
                        int amount = scan.nextInt();
                        int finalBalance = c.getBalance() + amount;
                        c.setBalance(finalBalance);
                        System.out.println("Cash deposit successful");
                        System.out.println("Your new balance is " + finalBalance);

                        //adding trans history
                        c.tType.add("CashDeposit");
                        c.amount.add(amount);
                        c.finalBal.add(c.getBalance());
                    }
                }
                if(!actNotFount){
                    System.out.println("Your account number is not found !");
                }
            }
            break;
            case 3 : {
                System.out.println("---Fund Transfer---");
                System.out.println("Enter your account number");
                int from = scan.nextInt();
                System.out.println("Enter your passcode");
                String fromPasscode = scan.next();
                for(customer c : customerDetails){
                    if(c.getAcctNumber() == from && decrypt(c.getPasscode()).equals(fromPasscode)){
                        System.out.println("Enter your TO account number");
                        int to = scan.nextInt();
                        for(customer cust : customerDetails){
                            if(cust.getAcctNumber() == to){
                                System.out.println("Enter the amount to be transfered");
                                int amt = scan.nextInt();
                                //condition to check the minimal bal in from account
                                if(c.getBalance() - amt >= 1000){
                                    //from object ' c '
                                    c.setBalance(c.getBalance()-amt);
                                    //to object 'cust'
                                    cust.setBalance(cust.getBalance() + amt);

                                    System.out.println("Money transfered successfully!");
                                    System.out.println("Your current account balance is "+ c.getBalance());

                                    //adding trans history
                                    c.tType.add("Transfered to "+ to);
                                    c.amount.add(amt);
                                    c.finalBal.add(c.getBalance());
                                    cust.tType.add("Transfered from " + from);
                                    cust.amount.add(amt);
                                    cust.finalBal.add(cust.getBalance());
                                }else{
                                    System.out.println("Fund not sufficient to transfer!");
                                }
                            }
                        }
                    }
                }
            }
            break;
            case 4 : {
                getTrasactionHistory();
                getChoice();
            }
            case 5 : {
                System.out.println("Provide value for N");
                Integer n = scan.nextInt();
                System.out.println("Displaying top " + n +" customer details");
                PriorityQueue<customer> queue = new PriorityQueue<>(Collections.reverseOrder());
                for(int i = 0 ; i < customerDetails.size() ; i++){

                }
                System.out.println("Customer ID" + " \t " + " Account Number " + " \t " + "Current Balance");

            }
        }
    }
    void getTrasactionHistory(){
        System.out.println("Enter the account number to display transaction history");
        int account = scan.nextInt();
        System.out.println("Enter passcode ");
        String pass = scan.next();
        for(customer c : customerDetails){
            //auth
            if(c.getAcctNumber() == account && decrypt(c.getPasscode()).equals(pass)){
                //display
                System.out.println("                Account Statement                        ");
                System.out.println("                Name " + c.getName() + "                 ");
                System.out.println("                Account number " + c.getAcctNumber() + "  ");
                int tID = 1;
                int length = c.tType.size();
                System.out.println("TransID \t TransType   \t   Amount   \t  Balance" );
                for(int i = 0 ; i < length ; i++){
                    System.out.println(tID + " \t " + c.tType.get(i) + "   \t  " + c.amount.get(i) + " \t  " + c.finalBal.get(i));
                }
                tID++;
            }
        }
    }
    void dummyFunction(){
        System.out.println("Do nothing...");
    }
    public static void main(String[] args) {
        Banking bank = new Banking();
        bank.getChoice();
    }
}

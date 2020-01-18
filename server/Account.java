package server;

import java.io.Serializable;


public class Account implements Serializable{
    //Instance variables for each account object
    private double balance;
    private String username, password;
    private int accountNumber;
    private boolean status;

    //static variable to control account numbers
    private static int nextAcNum = 1001;

    public Account (String uName, String pass, int acno, int balance) {
        
        this.username = uName;
        this.password = pass;
        this.accountNumber = acno;
        this.balance = balance;
	this.status=false;
        //increment account number, so next one will be updated
       // nextAcNum++;
    }

	public Account()
{
this.username = "nouser";
        this.password = "nopass";
	this.status=false;
}
    //getters and setters
    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean getFlag() {
        return this.status;
    }

    public void setFlag(boolean status) {
        this.status = status;
    }
   
/*
    @Override
    public String toString() {
        return this.accountNumber + " " + this.balance;
    }*/
}


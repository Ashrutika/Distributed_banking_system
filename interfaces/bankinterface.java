package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import server.Account;
//import java.io.*;

public interface bankinterface extends Remote{

	Account login(String username, String password) throws RemoteException;

	double deposit(int accountnum, double amount) throws RemoteException;

	double withdraw(int accountnum, double amount) throws RemoteException;
	
	Account logout(String username) throws RemoteException;		

	double transfer(int accountnum1,int accountnum2, double amt) throws RemoteException;

//	 Account accountDetails(long sessionID) throws RemoteException;

}

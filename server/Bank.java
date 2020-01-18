package server;

import interfaces.bankinterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;



public class Bank extends UnicastRemoteObject implements bankinterface
{

	private List<Account>accounts=new ArrayList<Account>();
	String query="";
	Account nouser=new Account();	
	String url,uname,pass;
	Connection con;
	Statement stmt;
	ResultSet rs;

	public Bank() throws RemoteException,Exception
    	{
       		super();
       		getAccounts();
	}

	public static void main(String args[])throws Exception 
	{
        	try 
		{
        		//Set up securitymanager for server, and specify path to the policy file
   			System.setProperty("java.security.policy", "file:/path_here/all.policy");
		            System.setSecurityManager(new SecurityManager());
		            System.out.println("\n--------------------\nSecurity Manager Set");

		            //Add bank to the RMI registry so it can be located by the client
        		    String name = "Bank";
       			     bankinterface bank = new Bank();
        		    Registry registry = LocateRegistry.getRegistry(Integer.parseInt(args[0]));
       			     registry.rebind(name, bank);
        		    //System.out.println("Bank Server Bound");
        		    System.out.println("Server Started\n--------------------\n");
        	} 
		catch (Exception e) 
		{
           		 e.printStackTrace();
        	}
}
		public void getAccounts() throws Exception
		{
			url="jdbc:mysql://localhost:3306/db_name;
			uname="name of mysql";
			 pass="password of mysql";
			
			Class.forName("com.mysql.cj.jdbc.Driver");  
			 con=DriverManager.getConnection(url,uname,pass);
			
			query="select * from user";
			stmt=con.createStatement();  
			rs=stmt.executeQuery(query);
		
			while(rs.next())
			{
				String name=rs.getString("username");
				String pwd=rs.getString("password");
				int id=rs.getInt("accountno");
				int bal=rs.getInt("balance");
				Account acc=new Account(rs.getString("username"),rs.getString("password"),rs.getInt("accountno"),rs.getInt("balance"));
				accounts.add(acc);
			
			}
			
		}
		

		@Override
		public Account login(String username, String password) throws RemoteException
		{
			//flag=true;
        		//Loop through the accounts to find the correct one for given username and password
			
        		for(Account acc : accounts) 
			{
            			if(username.equals(acc.getUserName()) && password.equals(acc.getPassword()))
				{
                			System.out.println(">> Account " + acc.getUserName() + " logged in");
					acc.setFlag(true);
              				return acc;
            			}
        		}
		return nouser;
        
		}

		@Override
		public Account logout(String username) throws RemoteException
		{
			//flag=true;
        		//Loop through the accounts to find the correct one for given username and password
        		for(Account acc : accounts) 
			{
            			if(username.equals(acc.getUserName()) && acc.getFlag()==true)
				{
                			System.out.println(">> Account " + acc.getUserName() + " logged out");
					acc.setFlag(false);
              				return acc;
            			}
        		}
		return nouser;
		}


		@Override
    		public double deposit(int accountnum, double amount) throws RemoteException 
		{
		
        
        	    try {
                		//Get the correct account
                		Account account = getAccount(accountnum);
				if(account.getFlag()==false)
				{
					System.out.println("user not logged in.");		
				}
				else
				{
                		account.setBalance(account.getBalance() + amount);
                		System.out.println(">> E" + amount + " deposited to account " + accountnum + "\n");
				System.out.println("new balance" + account.getBalance());
				query="update user set balance="+account.getBalance()+" where accountno="+accountnum;
				stmt=con.createStatement();  
				stmt.executeUpdate(query);

		                //return balance to client
        		        return account.getBalance();
				}
        		 } 
		   catch (Exception e) 
	    		{
                		e.printStackTrace();
            		}
        
     		   return -1;
    		}

		public Account getAccount(int acnum) 
		{
        		//Loop through the accounts to find one corresponding to account number passed from the client
        		//and return it
        		for(Account acc:accounts)
			{
            			if(acc.getAccountNumber() == acnum )
	    			{
                		return  acc;
            			}
				
        		}
		return accounts.get(3);	
        
	    	}

		@Override
    		public double withdraw(int accountnum, double amount) throws RemoteException
       		{
        
        	    try 
		    {
                		//Get correct user account based on accountnum
                		Account account = getAccount(accountnum);
				if(account.getFlag()==false)
				{
					System.out.println("user "+account.getAccountNumber()+" not logged in.");		
				}
				else
				{
                			//Check if withdrawal can be made, based on account balance
                			if (account.getBalance() > 0 && account.getBalance() - amount >= 0) 
					{
                    				account.setBalance(account.getBalance() - amount);


				                    System.out.println(">> E" + amount + " withdrawn from account " + accountnum + "\n");
						query="update user set balance="+account.getBalance()+" where accountno="+accountnum;
						stmt=con.createStatement();  
						stmt.executeUpdate(query);

			                    //return updated balance
                			    return account.getBalance();
                			}
					else
					return -2;	
				}
            	     }
		     catch (Exception e) 
			{
            		    e.printStackTrace();
            		}
			return -1;
    		}

		@Override
    		public double transfer(int accountnum1,int accountnum2, double amount) throws RemoteException
       		{
        
        	    try 
		    {
                		//Get correct user account based on accountnum
                		Account account1 = getAccount(accountnum1);
				Account account2 = getAccount(accountnum2);
				if(account1.getFlag()==false)
				{
					System.out.println("user "+account1.getAccountNumber()+" not logged in.");		
				}
				else
				{
                			//Check if withdrawal can be made, based on account balance
                			if (account1.getBalance() > 0 && account1.getBalance() - amount >= 0) 
					{

                    				account2.setBalance(account2.getBalance() + amount);
						account1.setBalance(account1.getBalance() - amount);

				           System.out.println(">> E" + amount + " transfered from account " + accountnum1 + "to account" + accountnum2 +"\n");
						query="update user set balance="+account1.getBalance()+" where accountno="+accountnum1;
						stmt=con.createStatement();  
						stmt.executeUpdate(query);

						query="update user set balance="+account2.getBalance()+" where accountno="+accountnum2;
						stmt=con.createStatement();  
						stmt.executeUpdate(query);
	
			                    //return updated balance
                			    return account1.getBalance();
						
                			}
					else
					return -2;	
				}
            	     }
		     catch (Exception e) 
			{
            		    e.printStackTrace();
            		}
			return -1;
    		}
	

}        

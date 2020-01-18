import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.collections.*; 

import interfaces.bankinterface;
import server.Account;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class client extends Application
{
Stage window;
Scene scene1,scene2,scene3,scene4,scene5;
    static int  serverPort, account,account1,account2;
    static String operation, username, password,serverAddress;
    //static long sessionID, id=0;
    static double amount,balance;
    static bankinterface bank;
	Account acc;

	public static void main (String args[]) 
	{
	        try {
        	    //Parse the command line arguments into the progam
        	   getCommandLineArguments(args);
        	    //Set up the rmi registry and get the remote bank object from it
        	    String name = "Bank";
        	    Registry registry = LocateRegistry.getRegistry(serverAddress,serverPort);
        	    bank = (bankinterface) registry.lookup(name);
        	    System.out.println("\n----------------\nClient Connected" + "\n----------------\n");
 		launch(args);
        	} 
	 	catch (Exception e)
		{
        	    e.printStackTrace();
        	    System.out.println(e);
        	}
        	
}
	

public void start(Stage primaryStage){
window=primaryStage;
window.setTitle("Login Page");
   Label  Message = new Label();
Label name = new Label("Name :");
TextField textName = new TextField();
name.setFont(Font.font ("Verdana", 20));
Label pass = new Label("Password :");
pass.setFont(Font.font ("Verdana", 20));
PasswordField textPass = new PasswordField();

textName.setMaxWidth(300);
textPass.setMaxWidth(300);

VBox hb = new VBox();
 hb.setPadding(new Insets(40,10,10,40));
hb.getChildren().addAll(name,textName);
hb.setSpacing(10);

VBox hb1 = new VBox();
 hb1.setPadding(new Insets(10,10,10,40));
hb1.getChildren().addAll(pass,textPass);
hb1.setSpacing(10);

Button btnlogin = new Button();
btnlogin.setText("Login");

VBox hb2 = new VBox();
 hb2.setPadding(new Insets(10,10,10,40));
hb2.getChildren().addAll(btnlogin,Message);
hb2.setSpacing(10);


VBox vb = new VBox();
vb.setPadding(new Insets(10,10,10,10));
vb.getChildren().addAll(hb,hb1,hb2);
vb.setSpacing(10);

StackPane layout1 = new StackPane();
layout1.getChildren().add(vb);
scene1 = new Scene(layout1,500,500);

//..............................scene2 design for transaction..................................................................................................................
Label accno = new Label();
Label user = new Label();
Label bal = new Label();

//..............................................................LOGIN...................................................................................................................................
btnlogin.setOnAction(new EventHandler<ActionEvent>(){            
public void handle(ActionEvent event){
   try {
				username=textName.getText().toString();
				password= textPass.getText().toString();
                	     acc = bank.login(username,password);

				if(acc.getUserName().equals("nouser") && acc.getPassword().equals("nopass") )
				{
				
				Message.setText("Incorrect user or password.");
        			Message.setTextFill(Color.RED);
				
				}
				else	
				{	
                	  
                    		window.setScene(scene2);
				 accno.setText("Account no. : "+acc.getAccountNumber());
				 user.setText("Name : "+textName.getText().toString());
				 bal.setText("Balance: "+acc.getBalance());
				window.setTitle("Account details of "+textName.getText().toString()+" Account no.: "+acc.getAccountNumber());
               			}
			}
		 	catch (RemoteException e) 
			{
                 	   e.printStackTrace();
                	} 

			

}
});

//..................................scene2 ....................................................
Label op = new Label("Select Operation"); 
op.setFont(Font.font ("Verdana", 20));
op.setPadding(new Insets(30,0,0,0));

 /*ToggleGroup tg = new ToggleGroup(); 
RadioButton r1 = new RadioButton("Deposit"); 
RadioButton r2 = new RadioButton("Withdraw"); 
RadioButton r3 = new RadioButton("Transfer"); 

 r1.setToggleGroup(tg); 
 r2.setToggleGroup(tg); 
 r3.setToggleGroup(tg); 
*/
Button r1 = new Button("Deposit"); 
Button r2 = new Button("Withdraw"); 
Button r3 = new Button("Transfer"); 

VBox s1 = new VBox();
s1.setPadding(new Insets(20,10,10,10));     //T,R,B,L
s1.getChildren().addAll(accno,user,bal);
s1.setSpacing(10);

Button logout = new Button("Logout");
logout.setStyle("-fx-text-fill: red");
logout.setStyle("-fx-background-color: skyblue");

VBox vb1 = new VBox();
vb1.setPadding(new Insets(10,10,10,40));
vb1.getChildren().add(logout);
vb1.setSpacing(40);


VBox s2 = new VBox();
s2.setPadding(new Insets(20,10,10,10));
s2.getChildren().addAll(vb1,s1,op,r1,r2,r3);
s2.setSpacing(10);

StackPane layout2 = new StackPane();
layout2.getChildren().add(s2);
scene2 = new Scene(layout2,500,500);



r1.setOnAction(new EventHandler<ActionEvent>(){                   //deposit scene2 radiobutton
public void handle(ActionEvent event){
				window.setScene(scene3);			
}
});

//.............................................................LOGOUT.........................................................................................................................
logout.setOnAction(new EventHandler<ActionEvent>(){              
public void handle(ActionEvent event){
		try
		{
				textName.clear();
				textPass.clear();
				Message.setText("");
				
				
					window.setScene(scene1);	
					
                    		System.out.println(username+"  successfully logged out\n" +
                                       "Account Number: " + acc.getAccountNumber() +
                                     
                                       "\nUsername: " + acc.getUserName() +
                                       "\nBalance: " + acc.getBalance() +
                                       "\n--------------------------\n");
                    
                		
		}
		catch (Exception e) 
			{
                 	   e.printStackTrace();
                	} 				
}
});

//......................Deposit page with accountno and amount..............................................................
Label depositl = new Label("Deposit"); 
depositl.setFont(Font.font ("Serif", 30));
Button back = new Button("Back");
back.setStyle("-fx-text-fill: #0000ff");
 back.setStyle("-fx-background-color: skyblue");

Label acountno = new Label("Account :");      
acountno.setFont(Font.font ("Verdana", 15));
TextField textacountno = new TextField();
Label amountt = new Label("Amount :");
amountt.setFont(Font.font ("Verdana", 15));
TextField textamt = new TextField();
Label userdepo = new Label();
Label totalbal = new Label();

Button depobtn = new Button("Deposit");                  
depobtn.setStyle("-fx-background-color: grey");

VBox backv = new VBox();
 backv.setPadding(new Insets(10,10,10,40));
backv.getChildren().addAll(back,depositl);
backv.setSpacing(40);


VBox vb2 = new VBox();
vb2.setPadding(new Insets(10,10,10,40));
vb2.getChildren().addAll(acountno,textacountno,amountt,textamt);
vb2.setSpacing(10);

VBox vb3 = new VBox();
vb3.setPadding(new Insets(10,10,10,40));
vb3.getChildren().addAll(backv,vb2,depobtn);
vb3.setSpacing(10);

VBox vb0 = new VBox();
vb0.setPadding(new Insets(10,10,10,40));
vb0.getChildren().addAll(vb3,userdepo,totalbal);
vb0.setSpacing(10);


back.setOnAction(new EventHandler<ActionEvent>(){  //scene3 back btn
public void handle(ActionEvent event){
				window.setScene(scene2);
				accno.setText("Account no. : "+acc.getAccountNumber());
				 user.setText("Name : "+textName.getText().toString());
				 bal.setText("Balance: "+balance);
				totalbal.setText("");	
				userdepo.setText("");
				textacountno.clear();
					textamt.clear();
				
}
});

//...................................................................................DEPOSIT...............................................................................................
depobtn.setOnAction(new EventHandler<ActionEvent>(){       //deposit btn onclick on scene3 deposit page 
public void handle(ActionEvent event){
         try{
			
			//totalbal.setText("");
			account=Integer.parseInt(textacountno.getText().toString());
			amount=Double.parseDouble(textamt.getText().toString());
			balance = bank.deposit(account, amount);	
			if(balance==-1)
			totalbal.setText("Please enter valid account no");
			else{
			userdepo.setText("Rs "+amount+" amount deposited to the account "+account);
                    	totalbal.setText("New balance : Rs"+balance);
			}
			}			
			catch (RemoteException e) 
			{
                    		e.printStackTrace();
                	} 
	 
	}
});

StackPane layout3 = new StackPane();
layout3.getChildren().add(vb0);
scene3 = new Scene(layout3,600,600);

//..............................onClickWithdraw button on scene2 open scene4(withdraw page)......................................................................
r2.setOnAction(new EventHandler<ActionEvent>(){
public void handle(ActionEvent event){
				window.setScene(scene4);			
}
});

//.............................................withdraw page (scene4)..............................................................................................
Label withdraww = new Label("Withdraw"); 
withdraww.setFont(Font.font ("Serif", 30));
Button back2 = new Button("Back");
back2.setStyle("-fx-text-fill: #0000ff");
back2.setStyle("-fx-background-color: skyblue");

Label wacountno = new Label("Account :");      
wacountno.setFont(Font.font ("Verdana", 15));
TextField wtextacountno = new TextField();
Label wamount = new Label("Amount :");
wamount.setFont(Font.font ("Verdana", 15));
TextField wtextamt = new TextField();
Label userwithdraw = new Label();
Label remainbal = new Label();

Button withdrawbtn = new Button("Withdraw");                  
withdrawbtn.setStyle("-fx-background-color: grey");

VBox back2v = new VBox();
 back2v.setPadding(new Insets(10,10,10,40));
back2v.getChildren().addAll(back2,withdraww);
back2v.setSpacing(40);

VBox vb4 = new VBox();
vb4.setPadding(new Insets(10,10,10,40));
vb4.getChildren().addAll(wacountno,wtextacountno,wamount,wtextamt);
vb4.setSpacing(10);


VBox vb5 = new VBox();
vb5.setPadding(new Insets(10,10,10,40));
vb5.getChildren().addAll(back2v,vb4,withdrawbtn);
vb5.setSpacing(10);

VBox wb0 = new VBox();
wb0.setPadding(new Insets(10,10,10,40));
wb0.getChildren().addAll(vb5,userwithdraw,remainbal);
wb0.setSpacing(10);

//..................back button on (scene4)withdraw page by onclick jumps to scene2(operation page) page......................................
back2.setOnAction(new EventHandler<ActionEvent>(){    
public void handle(ActionEvent event){
				window.setScene(scene2);
				accno.setText("Account no. : "+acc.getAccountNumber());
				 user.setText("Name : "+textName.getText().toString());
				 bal.setText("Balance: "+balance);
				userwithdraw.setText("");
				remainbal.setText("");	
				wtextacountno.clear();
				wtextamt.clear();		
}
});

//..........................................................WITHDRAW................................................................................................
withdrawbtn.setOnAction(new EventHandler<ActionEvent>(){       
public void handle(ActionEvent event){
          try 
			{
                    		//Make bank withdrawal and get updated balance
				account=Integer.parseInt(wtextacountno.getText().toString());
		               amount=Double.parseDouble(wtextamt.getText().toString());
                   		balance = bank.withdraw(account, amount);
				if(balance==-1)
				{
					userwithdraw.setText("Please enter valid account number.");
					
				}
				else if(balance==-2)
				{
					//System.out.println("Insufficient Funds.Please enter valid amount!");
					userwithdraw.setText("Insufficient Funds.Please enter valid amount!");
				}
				else
				{
					userwithdraw.setText("Successfully withdrew Rs "+amount+" from account "+account);
					remainbal.setText("Remaining Balance: Rs" +balance);
                    	
                		}
			} 
			catch (RemoteException e) 
			{	
                    		e.printStackTrace();
                	} 
}
});

StackPane layout4 = new StackPane();
layout4.getChildren().add(wb0);
scene4 = new Scene(layout4,600,600);

 

//..............................onClickTranserradiobutton on scene2 open scene5(Transfer page)......................................................................
r3.setOnAction(new EventHandler<ActionEvent>(){
public void handle(ActionEvent event){
				window.setScene(scene5);			
}
});
//..............................................................Transfer page(scene5).........................................................................
Label transl = new Label("Transfer"); 
transl.setFont(Font.font ("Serif", 30));
Button back3 = new Button("Back");
back3.setStyle("-fx-text-fill: #0000ff");
 back3.setStyle("-fx-background-color: skyblue");

Label tacountno1 = new Label("Enter your account no. :");    
TextField textacountno1 = new TextField();  
tacountno1.setFont(Font.font ("Verdana", 15));
Label tacountno2 = new Label("Account no.2 :");      
tacountno2.setFont(Font.font ("Verdana", 15));
TextField textacountno2 = new TextField();
Label tamount = new Label("Amount :");
tamount.setFont(Font.font ("Verdana", 15));
TextField tamtfield = new TextField();
Label usertrans = new Label();                                //label for displaying transaction details

Button transbtn = new Button("Transfer");                  
transbtn.setStyle("-fx-background-color: grey");

VBox back3v = new VBox();
 back3v.setPadding(new Insets(10,10,10,40));
back3v.getChildren().addAll(back3,transl);
back3v.setSpacing(40);

VBox vb7 = new VBox();
vb7.setPadding(new Insets(10,10,10,40));
vb7.getChildren().addAll(tacountno1,textacountno1,tacountno2,textacountno2,tamount,tamtfield);
vb7.setSpacing(10);

VBox vb8 = new VBox();
vb8.setPadding(new Insets(10,10,10,40));
vb8.getChildren().addAll(back3v,vb7,transbtn);
vb8.setSpacing(10);

VBox wb1 = new VBox();
wb1.setPadding(new Insets(10,10,10,40));
wb1.getChildren().addAll(vb8,usertrans);
wb1.setSpacing(10);

//..........................................................TRANSFER...............................................................................................
transbtn.setOnAction(new EventHandler<ActionEvent>(){       
public void handle(ActionEvent event){
           try 
			{
                    		//Make bank withdrawal and get updated balance
				//System.out.println(account1+" "+account2+" "+ amount);
				account1=Integer.parseInt(textacountno1.getText().toString());
				account2=Integer.parseInt(textacountno2.getText().toString());
				amount=Double.parseDouble(tamtfield.getText().toString());
                   		balance = bank.transfer(account1,account2, amount);
				if(balance==-1)
				{
					usertrans.setText("Please enter valid account number");
					//System.out.println(account1+"  "+account2+"  "+balance);
				}
				else if(balance==-2)
				{
					usertrans.setText("Insufficient Funds.Please enter valid amount!");
				}
				else
				{
					usertrans.setText("Successfully transfered " + amount + ".Rs from account " + account1 +" to account" + account2+
                                 "\nRemaining Balance: E" + balance);
                    		
                		}
			} 
			catch (RemoteException e) 
			{	
                    		e.printStackTrace();
                	} 
}
});

StackPane layout5 = new StackPane();
layout5.getChildren().add(wb1);
scene5 = new Scene(layout5,600,600);

 //..................back button on (scene5)transfer page by onclick jumps to scene2(operation page) page......................................
back3.setOnAction(new EventHandler<ActionEvent>(){    
public void handle(ActionEvent event){
				window.setScene(scene2);
				accno.setText("Account no. : "+acc.getAccountNumber());
				 user.setText("Name : "+textName.getText().toString());
				 bal.setText("Balance: "+balance);
				usertrans.setText("");	
				textacountno1.setText("");
				textacountno2.setText("");
				tamtfield.setText("");		
}
});


window.setScene(scene1);
window.show();
}
public static void getCommandLineArguments(String args[])throws Exception 
    	{
        
        	serverPort = Integer.parseInt(args[1]);
		serverAddress= args[0];
        	
    	}

}


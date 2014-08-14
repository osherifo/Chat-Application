

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class chClient implements Runnable{
	Socket clientSocket;
	DataInputStream input;
	PrintStream output;
	String clientName;
	BufferedReader myinput;
	public static final int PORT_NUMBER=5000;
	
	
	public chClient(DataInputStream ip){
		input=ip;
		
	}
	public chClient(){
	try {
		
		clientSocket = new Socket("localhost",PORT_NUMBER);
		input= new DataInputStream(clientSocket.getInputStream());
		output = new PrintStream(clientSocket.getOutputStream());
		myinput=new BufferedReader(new InputStreamReader(System.in));
		
        execute();   
		
	} catch (UnknownHostException e) {
		
		e.printStackTrace();
	} catch (IOException e) {

		e.printStackTrace();
	}	
		
		
	}
	
	public void execute(){
		try{
		if(input.readLine().equals("connection established"))
			System.out.println("enter name");
			String status;
	
		    clientName=myinput.readLine();
		    output.println(clientName);
		    status=input.readLine();
		    while(status.equals("Taken")){
		    	System.out.println("Name taken,Enter another name");
		    	clientName=myinput.readLine();
			    output.println(clientName);
			    status=input.readLine();
		    }
	
	
		    System.out.println(input.readLine());
		   Thread printstream= new Thread(new chClient(input));
		   printstream.start();
			String ip;
			while(!((ip=myinput.readLine()).equals("QUIT"))){
				
				if(ip.equals("MEMBER_LIST_REQUEST"))
					output.println(ip);
				else{
				String protocolmessage=clientName+" "+ip;
				output.println(protocolmessage);
				
				}
			}
			
			output.println("QUIT "+clientName);
				
			output.flush();
			clientSocket.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
public void requestMemberList(){
		output.println("MEMBER_LIST_REQUEST");
	}

public void sendMessage(String message){
	output.println(clientName+" "+message);
}

public void quitServer(){
	try {
		output.println("QUIT "+clientName);
		output.flush();
		clientSocket.close();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	
}






public void run(){
boolean loop=true;
	while(loop){
		try {String line;
			if((line=input.readLine())!=null)
			System.out.println(line);
		} catch (SocketException e) {
		
			loop=false;
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}






public static void main(String[] args) {
	chClient tclient =new chClient();
	//tclient.execute();
}





}

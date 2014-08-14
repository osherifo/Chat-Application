package chapp.server;



import java.io.DataInputStream;

import java.io.IOException;

import java.io.PrintStream;



import java.net.Socket;
import java.util.StringTokenizer;

public class ClientThread extends Thread {
	chServer mainServer;
	Socket servedSocket;
	DataInputStream input;
	PrintStream output;
	String userName;
	
	public ClientThread(chServer ms,Socket ss){
		mainServer=ms;
		servedSocket=ss;
		try{
	    input = new DataInputStream(servedSocket.getInputStream());
	    output=new PrintStream(servedSocket.getOutputStream());
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
		
		
		
		}
	
	
	
	
	
	
	
	
	public void run(){
	
	try {
		
		output.println("connection established");

		
		userName=input.readLine();
		while(!mainServer.addUserName(userName)){
			output.println("Taken");
			userName=input.readLine();
			
		}

		
		output.println("OK");
		mainServer.broadcast(userName+" has entered the room","Server");
	} catch (IOException e1) {
		
		e1.printStackTrace();
	}	
	
		
	try{	
	loop: while(true){
			
				String line = input.readLine();
				StringTokenizer st = new StringTokenizer(line," ",false);
				if(line.startsWith("QUIT")){
					st.nextToken();
					if(st.nextToken().equals(userName))
						break loop;
					else output.println("COMMAND invalid command");
					
				}
			
				else if(line.equals("MEMBER_LIST_REQUEST")){
					output.println("MEMBER_LIST_REQUEST "+mainServer.Usernames());
				}
				else{
					String uname = st.nextToken();
					
					if(uname.equals(userName)){
						String message=line.substring(uname.length()+1);
						mainServer.broadcast(message,uname);
					}
				}
			
			
		
			output.flush();
			
			
			
			
		}
		
		
	    input.close();
		output.close();
		servedSocket.close();
		mainServer.QuitNotification(userName);
		mainServer.removeUserName(userName);
		mainServer.removeThread(this);
		
		
		
	}
	catch(IOException e){
		e.printStackTrace();
	}
	
	}

}

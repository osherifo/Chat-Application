
 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

public class chServer {
public ArrayList<ClientThread> threads;
public ArrayList<String> users;
public static final int PORT_NUMBER=5000;	

public chServer(){
	threads = new ArrayList<ClientThread>();
	users = new ArrayList<String>();
}
	
public void execute(){
	
	try{
	ServerSocket mainsocket=new ServerSocket(PORT_NUMBER);
	while(true){
		Socket current = mainsocket.accept();
		DataInputStream is = new DataInputStream(current.getInputStream());
		PrintStream test=new PrintStream(current.getOutputStream());
		ClientThread client = new ClientThread(this,current);
		client.start();
		threads.add(client);
	/*	test.println("what is your name");
		test.flush();
		
		System.out.println(is.readLine());
		String line;
		while(!((line = is.readLine()).equals("quit"))){
			System.out.println(line);
		}*/
		
		
	}
	}
	catch(IOException e){
		e.printStackTrace();
	}
}





public String Usernames(){
	String userlist="";
	for(String s:users){
		userlist+=s+" ";
	}
	return userlist;
}
public void removeUserName(String userName){
	users.remove((String)userName);
	
}
public boolean userNameTaken(String userName){
	return users.contains((String)userName);
}
public boolean addUserName(String name){
	if(!userNameTaken(name)){
		users.add(name);
	return true;}
	return false;
}
public void removeThread(Thread thread){
	threads.remove((ClientThread)thread);
}
	

public void broadcast(String message,String user){
	String msg=user+": "+message;
	for(ClientThread ct:threads){
		ct.output.println(msg);
	}
}
public void QuitNotification(String userName){
  broadcast(userName+" has left the room","Server");
	
	
}

public static void main(String[] args) {
	chServer tserver=new chServer();
	tserver.execute();
}
	
}

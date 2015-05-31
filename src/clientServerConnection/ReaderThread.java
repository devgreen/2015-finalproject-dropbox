package clientServerConnection;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import messages.Message;

public class ReaderThread extends Thread{
	
	private Socket clientSocket;
	private LinkedBlockingQueue<Message> messages;

	public ReaderThread(Socket clientSocket, LinkedBlockingQueue<Message> messages){
		
		this.clientSocket = clientSocket;
		this.messages = messages;
		
	}
	
	@Override
	public void run(){
		//wrap client socket in object input stream 
		//while(true)
		// cast (Message) use readObject()
		// messages.add(message);
		
	}
	
}

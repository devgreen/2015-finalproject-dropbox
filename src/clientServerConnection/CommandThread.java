package clientServerConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CommandThread extends Thread {

	private Socket clientSocket;
	private Server server;
	private PrintWriter writer;

	public CommandThread(Socket clientSocket, Server server){
		this.clientSocket = clientSocket;
		this.server = server;
		try{
			writer = new PrintWriter(clientSocket.getOutputStream());
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		writer.println("LIST");
		
	}

}

package clientServerConnection;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import messages.Message;

public class Server implements Incoming{

	private LinkedBlockingQueue<Message> messages;
	private List<Socket> sockets;
	private PerformThread perform;
	private ServerSocket serverSocket;

	public Server() {
		messages = new LinkedBlockingQueue<Message>();
		sockets = new ArrayList<Socket>();
		// instantiate the serverSocket
		perform = new PerformThread(messages);
		perform.start();
	}

	public void connectToClients() {
		
	}

	

	@Override
	public void dealWithMessage(Message message) {
		messages.add(message);
		
	}

}

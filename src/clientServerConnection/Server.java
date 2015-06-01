package clientServerConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import messages.Message;

public class Server implements Incoming {

	private LinkedBlockingQueue<Message> messages;
	private List<Socket> sockets;
	private PerformThread perform;
	private ServerSocket serverSocket;

	public Server() throws IOException {
		serverSocket = new ServerSocket(1113);
		messages = new LinkedBlockingQueue<Message>();
		sockets = new ArrayList<Socket>();
		perform = new PerformThread(messages);
		perform.start();
	}

	public void connectToClients() {
		while (true) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				sockets.add(socket);
				ReaderThread thread = new ReaderThread(socket, this);
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void dealWithMessage(Message message) {
		messages.add(message);

	}

}

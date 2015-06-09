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
	private FileCache fileCache;

	public Server() throws IOException {
		serverSocket = new ServerSocket(1119);
		messages = new LinkedBlockingQueue<Message>();
		sockets = new ArrayList<Socket>();
		perform = new PerformThread(messages);
		perform.start();
		fileCache = new FileCache(FileCache.ROOT + "/server");
	}
	
	public List<Socket> getSockets() {
		return sockets;
	}

	public LinkedBlockingQueue<Message> getMessages(){
		return messages;
	}
	
	@Override
	public FileCache getFileCache(){
		return fileCache;
	}

	public void connectToClients() {

		while (true) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				sockets.add(socket);
				//create a thread so that the server can read from the client
				new ServerReaderThread(socket, this).start();
				//create a thread in which commands are sent from the server to the client
				//new ServerCommandThread(socket, this).start();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void dealWithMessage(Message message) {
		System.out.println("server got message, adding to queue");
		messages.add(message);

	}

}

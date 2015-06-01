package clientServerConnection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import messages.Message;

public class Client implements Incoming {

	private Socket socket;
	private FileCache fileCache;

	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 1113);
		ReaderThread thread = new ReaderThread(socket, this);
		thread.start();
		fileCache = new FileCache();
	}

	@Override
	public void dealWithMessage(Message message) {
		message.display();
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public FileCache getFileCache(){
		return fileCache;
	}

}

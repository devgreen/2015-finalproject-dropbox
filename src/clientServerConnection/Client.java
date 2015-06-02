package clientServerConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

import messages.Message;

public class Client implements Incoming {

	private Socket socket;
	private FileCache fileCache;
	private OutputStream out;
	private PrintWriter writer;
	private JTextArea area;

	public Client(JTextArea area) throws UnknownHostException, IOException {
		socket = new Socket("localhost", 1113);
		ReaderThread thread = new ReaderThread(socket, this);
		thread.start();
		fileCache = new FileCache();
		out = socket.getOutputStream();
		writer = new PrintWriter(out);
		this.area = area;
	}

	public JTextArea getArea() {
		return area;
	}

	@Override
	public void dealWithMessage(Message message) {
		System.out.println ("displaying");
		message.display();
	}

	public Socket getSocket() {
		return socket;
	}

	public FileCache getFileCache() {
		return fileCache;
	}

	public PrintWriter getWriter() {
		return writer;
	}
	
	public void write(String message){
		writer.println(message);
		writer.flush();
	}

}

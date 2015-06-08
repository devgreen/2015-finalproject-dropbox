package clientServerConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JTextArea;

import messages.Message;

public class Client implements Incoming {

	private Socket socket;
	private FileCache fileCache;
	private OutputStream out;
	private PrintWriter writer;
	private JTextArea area;
	private ArrayList<String> serverFiles;

	public Client(JTextArea area) throws UnknownHostException, IOException {
		socket = new Socket("localhost", 1113);
		new ClientReaderThread(socket, this).start();
		fileCache = new FileCache("C:/Users/Devora/Documents/client");
		out = socket.getOutputStream();
		writer = new PrintWriter(out);
		this.area = area;
		serverFiles = new ArrayList<String>();
		new ClientCommandThread(socket, this).start();
	}

	public JTextArea getArea() {
		return area;
	}

	@Override
	public void dealWithMessage(Message message) {
		System.out.println("displaying");
		message.display();
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public FileCache getFileCache() {
		return fileCache;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void write(String message) {
		System.out.println (message);
		writer.println(message);
		writer.flush();
	}

	public void addToServersFiles(String filesListRcvd) {
		// gets the string from the file message, so need to split it to get
		// just the filename - that's all we need
		String[] temp = filesListRcvd.split(" ");
		serverFiles.add(temp[1]);
	}

	public ArrayList<String> getServerFiles() {
		return serverFiles;
	}

}

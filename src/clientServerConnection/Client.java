package clientServerConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JTextArea;

import messages.Message;

public class Client implements Incoming {

	private LinkedBlockingQueue<Message> messages;
	private Socket socket;
	private FileCache fileCache;
	private OutputStream out;
	private PrintWriter writer;
	private JTextArea area;
	private ArrayList<String> serverFiles;
	private Map<String, Long> serverFileInfo;

	public Client(JTextArea area) throws UnknownHostException, IOException {
		socket = new Socket("localhost", 2224);
		new ClientReaderThread(socket, this).start();
		out = socket.getOutputStream();
		writer = new PrintWriter(out);
		fileCache = new FileCache(FileCache.ROOT + "/client");
		this.area = area;
		serverFiles = new ArrayList<String>();
		messages = new LinkedBlockingQueue<Message>();
		serverFileInfo = new HashMap<String, Long>();
		new ClientCommandThread(socket, this).start();
		new PerformThread(messages).start();
	}

	public JTextArea getArea() {
		return area;
	}

	@Override
	public void dealWithMessage(Message message) {
		messages.add(message);
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
		System.out.println(message);
		writer.println(message);
		writer.flush();
	}

	public void addToServersFiles(String filesListRcvd) {
		// gets the string from the file message, so need to split it to get
		// just the filename - that's all we need
		serverFiles.add(filesListRcvd);
	}
	public void addToServerFileInfo(String fileName, Long lastModified){
		serverFileInfo.put(fileName, lastModified);
	}
	
	public Map<String, Long> getServerFileInfo(){
		return serverFileInfo;
	}

	public ArrayList<String> getServerFiles() {
		return serverFiles;
	}

}

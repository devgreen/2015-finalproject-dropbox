package clientServerConnection;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientCommandThread extends Thread {

	// private Socket clientSocket;
	private Client client;
	private PrintWriter writer;

	public ClientCommandThread(Socket clientSocket, Client client) {
		// this.clientSocket = clientSocket;
		this.client = client;
		try {
			writer = new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		client.write("LIST");
		List<File> clientFiles = client.getFileCache().getFiles();
		while (true) {
			if (!clientFiles.equals(client.getFileCache().getFiles())) {
				List<File> missing = new ArrayList<File>(client.getFileCache().getFiles());
				missing.removeAll(clientFiles);
				for (int i = 0; i < missing.size(); i++) {
					client.write("CHUNK" + " "+ missing.get(i).getName()+ " 0" + " " + missing.get(i).length());
				}
				clientFiles = client.getFileCache().getFiles();
				client.write("LIST");
			}
		}

	}

}

package clientServerConnection;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerCommandThread extends Thread {

	private Socket clientSocket;
	private Server server;
	private PrintWriter writer;

	public ServerCommandThread(Socket clientSocket, Server server) {
		this.clientSocket = clientSocket;
		this.server = server;
		try {
			writer = new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		//writer.println("LIST");
		List<File> serverFiles = server.getFileCache().getFiles();
		while (true) {
			if (!serverFiles.equals(server.getFileCache().getFiles())) {
				System.out.println ("im in the thread");
				List<File> missing = new ArrayList(server.getFileCache().getFiles());
				missing.removeAll(serverFiles);
				for (int i = 0; i < missing.size(); i++) {
					writer.println("DOWNLOAD");
				}
				serverFiles = server.getFileCache().getFiles();
			}
		}

	}

}

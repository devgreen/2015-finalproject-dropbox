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
		// client asks for list of files, gets the list of files and one by one
		// adds it to its arraylist of server's files.

		// Now it has to compare its files to the ones in the server files list
		// and downloads and uploads appropriately.
		ArrayList<String> clientFiles = client.getFileCache()
				.getFilesAsString();
		ArrayList<String> serverFiles = client.getServerFiles();
		ArrayList<String> clientFilesToBeUploaded = new ArrayList<String>();
		for (String clientFile : clientFiles) {
			if (!serverFiles.contains(clientFile)) {
				clientFilesToBeUploaded.add(clientFile);
			}
		}
		ArrayList<String> serverFilesToBeDownloaded = new ArrayList<String>();
		for (String serverFile : serverFiles) {
			if (!clientFiles.contains(serverFile)) {
				serverFilesToBeDownloaded.add(serverFile);
			}
		}

		for (String fileName : serverFilesToBeDownloaded) {
			client.write("DOWNLOAD " + fileName);
		}
		
		for(String fileName: clientFilesToBeUploaded){
			uploadFile(fileName);
		}

		// Then it constantly checks to see if something new has been added to
		// its file cache.
		// If so, it uploads it to the Server.
		/*
		 * List<File> clientFiles = client.getFileCache().getFiles(); while
		 * (true) { if (!clientFiles.equals(client.getFileCache().getFiles())) {
		 * List<File> missing = new
		 * ArrayList<File>(client.getFileCache().getFiles());
		 * missing.removeAll(clientFiles); for (int i = 0; i < missing.size();
		 * i++) { client.write("CHUNK" + " "+ missing.get(i).getName()+ " 0" +
		 * " " + missing.get(i).length()); } clientFiles =
		 * client.getFileCache().getFiles(); client.write("LIST"); } }
		 */


	}

	private void uploadFile(String fileName) {
		File file = new File(fileName);
		int start = 0;
		long fileSize = file.length();
		int size = 0;

		while (fileSize > 0) {
			if (fileSize >= 512) {
				size = 512;
				fileSize -= 512;
			} else {
				size = (int) fileSize;
				fileSize = 0;
			}
			Chunk chunk = new Chunk(fileName, start, size);
			String chunkStr = chunk.toString();
			writer.println(chunkStr);
			writer.flush(); 
			start += size + 1; 
			}
		}
	}


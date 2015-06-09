package clientServerConnection;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ClientCommandThread extends Thread {

	// private Socket clientSocket;
	private Client client;
	private PrintWriter writer;
	private ArrayList<String> clientFilesToBeUploaded;

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

		ArrayList<String> clientFiles;
		ArrayList<String> serverFiles;
		Map <String, Long> serverFileInfo;
		clientFilesToBeUploaded = new ArrayList<String>();
		ArrayList<String> serverFilesToBeDownloaded = new ArrayList<String>();

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clientFiles = client.getFileCache().getFilesAsString();
			serverFiles = client.getServerFiles();
			Map<String, Long> clientFileInfo = client.getFileCache().getFileInfo();
			serverFileInfo = client.getServerFileInfo();

			clientFilesToBeUploaded.clear();
			/*for (String clientFile : clientFiles) {
				if (!serverFiles.contains(clientFile)) {
					clientFilesToBeUploaded.add(clientFile);
				}
			}*/
			for (String clientFile : clientFiles) {
				if (!serverFileInfo.containsKey(clientFile)) {
					clientFilesToBeUploaded.add(clientFile);
				}
				else if (serverFileInfo.containsKey(clientFile)){
					if (!(serverFileInfo.get(clientFile) == clientFileInfo.get(clientFile))){
						clientFilesToBeUploaded.add(clientFile);
					}
				}
			}

			serverFilesToBeDownloaded.clear();
			for (String serverFile : serverFiles) {
				if (!clientFileInfo.containsKey(serverFile)) {
					serverFilesToBeDownloaded.add(serverFile);
				}
				else if (clientFileInfo.containsKey(serverFile)){
					if (!(clientFileInfo.get(serverFile) == serverFileInfo.get(serverFile))){
						serverFilesToBeDownloaded.add(serverFile);
					}
				}
			}

			for (int i = 0; i < clientFilesToBeUploaded.size(); i++) {
				uploadFile(clientFilesToBeUploaded.get(i), i);
			}

			for (String fileName : serverFilesToBeDownloaded) {
				System.out.println("sending download command for " + fileName);
				client.write("DOWNLOAD " + fileName);
			}

		}

	}

	private void uploadFile(String fileName, int i) {
		// File file = new File(FileCache.ROOT + "/server/" + fileName);
		File fileUploading = new File(FileCache.ROOT + "/client/" + fileName);
		int start = 0;
		long fileSize = fileUploading.length();
		int size = 0;

		while (fileSize > 0) {
			if (fileSize >= 512) {
				size = 512;
				fileSize -= 512;
			} else {
				size = (int) fileSize;
				fileSize = 0;
			}
			Chunk chunk = new Chunk(FileCache.ROOT + "/client/" + fileName, start, size);
			String chunkStr = chunk.toString();
			writer.println(chunkStr);
			writer.flush();
			start += size;
		}
	}
}

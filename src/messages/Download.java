package messages;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import clientServerConnection.Chunk;
import clientServerConnection.FileCache;
import clientServerConnection.Incoming;
import clientServerConnection.Server;

public class Download implements Message {

	private Server server;
	private String[] downloadCommand;

	public Download(Incoming server, String[] downloadCommand) {
		this.server = (Server) server;
		this.downloadCommand = downloadCommand;
	}

	@Override
	public void perform() {
		String fileName = downloadCommand[1];
		int offset = Integer.parseInt(downloadCommand[2]);
		int chunkSize = Integer.parseInt(downloadCommand[3]);

		FileCache serverFileCache = server.getFileCache();
		FileCache clientFileCache = client.getFileCache();
		File file = new File(fileName);
		long size = file.length();
		chunkSize = (int) (size/512);

		//int length = fileSize / 512;
		while (size > 0) {
			try {
				Chunk chunk = serverFileCache.getChunk(fileName, offset, chunkSize);
				clientFileCache.addChunk(chunk);
				offset = (int) (size - chunkSize);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void display() {

	}

}

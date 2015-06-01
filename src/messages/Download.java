package messages;

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
		String filename = downloadCommand[1];
		int offset = Integer.parseInt(downloadCommand[2]);
		int chunksize = Integer.parseInt(downloadCommand[3]);

		FileCache serverFileCache = server.getFileCache();
		FileCache clientFileCache = client.getFileCache();

		int length = fileSize / 512;
		while (fileSize > 0) {
			try {
				Chunk chunk = serverFileCache.getChunk(filename, offset,
						chunkSize);
				clientFileCache.addChunk(chunk);
				offset = fileSize - length;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void display() {
		// TODO Auto-generated method stub

	}

}

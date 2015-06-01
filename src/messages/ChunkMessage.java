package messages;

import java.io.IOException;
import java.io.PrintWriter;

import clientServerConnection.Chunk;
import clientServerConnection.FileCache;
import clientServerConnection.Incoming;
import clientServerConnection.Server;

public class ChunkMessage implements Message {

	//Need access to CLIENT
	private Server server;
	private String[] chunkCommand;

	public ChunkMessage(Incoming server,
			String[] chunkCommand) {
		this.server = (Server) server;
		this.chunkCommand = chunkCommand;
	}

	@Override
	public void perform() {
		FileCache serverFileCache = server.getFileCache();
		FileCache clientFileCache = client.getFileCache();
		String fileName = chunkCommand[1];
		// Long lastModified = Long.parseLong(chunkCommand[2]);
		int fileSize = Integer.parseInt(chunkCommand[3]);
		int offset = Integer.parseInt(chunkCommand[4]);
		// String base64encodedbytes = chunkCommand[5];
		int length = fileSize / 512;
		while (fileSize > 0) {
			try {
				Chunk chunk = clientFileCache.getChunk(fileName, offset, length);
				serverFileCache.addChunk(chunk);
				offset = fileSize - length;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void display() {
		// Println "Upload on Process" on client gui, if exists
	}

}

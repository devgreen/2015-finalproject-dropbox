package messages;

import java.io.IOException;
import clientServerConnection.Chunk;
import clientServerConnection.FileCache;
import clientServerConnection.Incoming;

public class ChunkMessage implements Message {

	private Incoming incoming;
	private String[] chunkCommand;

	public ChunkMessage(Incoming incoming, String[] chunkCommand) {
		this.incoming = incoming;
		this.chunkCommand = chunkCommand;
	}

	@Override
	public void perform() {
		// Whether the server or client receives a Chunk command, its job is to
		// take the Chunk details, create a Chunk object, and add it to its
		// FileCache.
		
		String fileName = chunkCommand[1];
		int offSet = Integer.parseInt(chunkCommand[2]);
		int size = Integer.parseInt(chunkCommand[3]);
		Chunk chunk = new Chunk(fileName, offSet, size);
		FileCache fileCache = incoming.getFileCache();
		try {
			fileCache.addChunk(chunk);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void display() {
		// Println "Upload on Process" on client gui, if exists
	}

}

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

		// if have patience, split into separate named variables - filename,
		// offset, size
		Chunk chunk = new Chunk(chunkCommand[1], Integer.parseInt(chunkCommand[4]), Integer.parseInt(chunkCommand[3]));
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

/*
 * String fileName = "HelloWorld.txt"; int start = 0; File file = new
 * File(fileName); long fileSize = file.length(); int size = 0;
 * 
 * while (fileSize > 0) { if (fileSize >= 512) { size = 512; fileSize -= 512; }
 * else { size = (int) fileSize; fileSize = 0; } Chunk chunk = new
 * Chunk(fileName, start, size); String chunkStr = chunk.toString(); //
 * writer.println(chunkStr); // writer.flush; start += size + 1; }
 */

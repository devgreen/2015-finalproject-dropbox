package messages;

import java.io.IOException;

import clientServerConnection.Chunk;
import clientServerConnection.FileCache;
import clientServerConnection.Incoming;

public class ClientChunkMessage implements Message {

	private Incoming incoming;
	private String[] chunkCommand;

	public ClientChunkMessage(Incoming incoming, String[] chunkCommand) {
		this.incoming = incoming;
		this.chunkCommand = chunkCommand;
	}

	@Override
	public void perform() {
		// Whether the server or client receives a Chunk command, its job is to
		// take the Chunk details, create a Chunk object, and add it to its
		// FileCache.

		String fileName = FileCache.ROOT + "/client/" + chunkCommand[1];
		int offSet = Integer.parseInt(chunkCommand[4]);
		long dateModified = Long.parseLong(chunkCommand[2]);
		int size = Integer.parseInt(chunkCommand[3]);
		String encodedBytes = chunkCommand[5];
		Chunk chunk = new Chunk(fileName, offSet, size,dateModified, encodedBytes);
		FileCache fileCache = incoming.getFileCache();
		try {
			fileCache.addChunk(chunk);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

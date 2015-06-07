package messages;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import clientServerConnection.Chunk;
import clientServerConnection.FileCache;
import clientServerConnection.Server;

public class ServerChunkMessage implements Message {

	private Server incoming;
	private String[] chunkCommand;
	private List<Socket> sockets;

	public ServerChunkMessage(Server server, String[] chunkCommand) {
		this.incoming = server;
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

		if (size - offSet <= 512) {
			sockets = incoming.getSockets();
			Iterator<Socket> iter = sockets.iterator();
			while (iter.hasNext()) {
				Socket socket = iter.next();
				try {
					OutputStream out = socket.getOutputStream();
					PrintWriter writer = new PrintWriter(out);
					writer.println("SYNC" + " " + chunkCommand[1] + " " + chunkCommand[3]);
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
					iter.remove();
				}
			}

		}

	}

	@Override
	public void display() {
		// Println "Upload on Process" on client gui, if exists
	}

}

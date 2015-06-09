package messages;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import clientServerConnection.Chunk;
import clientServerConnection.FileCache;
import clientServerConnection.Incoming;
import clientServerConnection.Server;

public class ServerChunkMessage implements Message {

	private Server server;
	private String[] chunkCommand;
	private List<Socket> sockets;

	public ServerChunkMessage(Incoming server, String[] chunkCommand) {
		this.server = (Server) server;
		this.chunkCommand = chunkCommand;
	}

	@Override
	public void perform() {
		// Whether the server or client receives a Chunk command, its job is to
		// take the Chunk details, create a Chunk object, and add it to its
		// FileCache.

		String fileName = FileCache.ROOT + "/server/" + chunkCommand[1];
		int offSet = Integer.parseInt(chunkCommand[4]);
		int size = Integer.parseInt(chunkCommand[2]);
		long dateModified = Long.parseLong(chunkCommand[3]);
		String encodedBytes = chunkCommand[5];
		Chunk chunk = new Chunk(fileName, offSet, size, dateModified,encodedBytes);
		FileCache fileCache = server.getFileCache();
		try {
			fileCache.addChunk(chunk);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//finished uploading entire file
		if (size - offSet < 512) {
			//send sync message to all sockets
			sockets = server.getSockets();
			Iterator<Socket> iter = sockets.iterator();
			while (iter.hasNext()) {
				Socket socket = iter.next();
				try {
					OutputStream out = socket.getOutputStream();
					PrintWriter writer = new PrintWriter(out);
					writer.println("SYNC" + " " + chunkCommand[1] + " "
							+ chunkCommand[3]);
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
					iter.remove();
				}
			}
			
			

		}

	}

}

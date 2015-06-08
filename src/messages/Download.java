package messages;

import java.io.File;
import java.io.PrintWriter;
import clientServerConnection.Chunk;

public class Download implements Message {

	private PrintWriter writer;
	private String[] downloadCommand;

	public Download(PrintWriter writer, String[] downloadCommand) {
		this.writer = writer;
		this.downloadCommand = downloadCommand;
	}

	@Override
	public void perform() {
		// a server's response to a download command is to take the file,
		// separate it into Chunks and send those Chunk commands across the
		// socket.
		String fileName = downloadCommand[1];
		File file = new File("C:/Users/Devora/Documents/server/" + fileName);
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


	@Override
	public void display() {

	}

}

/*
 * FileCache fileCache = incoming.getFileCache(); try { Chunk chunk =
 * fileCache.getChunk(downloadCommand[1], Integer.parseInt(downloadCommand[4]),
 * Integer.parseInt(downloadCommand[3])); //writer.println(chunk.toString); }
 * catch (IOException e) { e.printStackTrace(); }
 * 
 * 
 * 
 * String fileName = downloadCommand[1]; int offset =
 * Integer.parseInt(downloadCommand[2]); int chunkSize =
 * Integer.parseInt(downloadCommand[3]);
 * 
 * FileCache serverFileCache = server.getFileCache(); FileCache clientFileCache
 * = client.getFileCache(); File file = new File(fileName); long size =
 * file.length(); chunkSize = (int) (size/512);
 * 
 * //int length = fileSize / 512; while (size > 0) { try { Chunk chunk =
 * serverFileCache.getChunk(fileName, offset, chunkSize);
 * clientFileCache.addChunk(chunk); offset = (int) (size - chunkSize); } catch
 * (IOException e) { e.printStackTrace(); } }
 */

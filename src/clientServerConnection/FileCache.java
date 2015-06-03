package clientServerConnection;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

public class FileCache {

	// this exists on the hard drive

	private static final String ROOT = "C:/Users/Rachel Aziza/Documents";
	

	public FileCache() {
		new File(ROOT).mkdir();
		// then do dropbox/username/filename

	}

	public List<File> getFiles() {
		File file = new File (ROOT);
		File [] lists = file.listFiles();
		return Arrays.asList(lists);
	}

	public void addChunk(Chunk chunk) throws IOException {
		String fileName = chunk.getFileName();
		File file = new File(fileName);
		RandomAccessFile random = new RandomAccessFile(file, "rw");
		random.seek(chunk.getOffSet());
		random.write(chunk.getBytes());
		random.close();
		
	}

	public Chunk getChunk(String fileName, int start, int length) throws IOException {
		File file = new File(fileName);
		byte [] bytes = new byte[length] ; 
		RandomAccessFile random = new RandomAccessFile(file, "rw");
		random.read(bytes, start,  length);
		random.close();
		return new Chunk(fileName, start, length);
	}
	
}

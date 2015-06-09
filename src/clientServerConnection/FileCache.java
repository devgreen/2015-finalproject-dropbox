package clientServerConnection;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCache {

	// this exists on the hard drive

	// public static final String ROOT =
	// "C:/Users/Devora/Documents/GitHub/2015-finalproject/dropbox";
	public static final String ROOT = "./";
	private String directory;

	public FileCache(String directory) {
		this.directory = directory;
		new File(directory).mkdir();
		// new File(ROOT).mkdir();
	}

	public List<File> getFiles() {
		File file = new File(directory);
		File[] lists = file.listFiles();
		return Arrays.asList(lists);
	}

	public ArrayList<String> getFilesAsString() {
		File file = new File(directory);
		File[] lists = file.listFiles();
		ArrayList<String> fileNames = new ArrayList<String>();
		for (File aFile : lists) {
			fileNames.add(aFile.getName());
		}
		return fileNames;
	}

	public Map<String, Long> getFileInfo() {
		File file = new File(directory);
		File[] lists = file.listFiles();
		Map<String, Long> fileInfo = new HashMap<String, Long>();
		for (File aFile : lists) {
			fileInfo.put(aFile.getName(), aFile.lastModified());
		}
		return fileInfo;
	}

	public void addChunk(Chunk chunk) throws IOException {
		String fileName = chunk.getFileName();
		File file = new File(fileName);
		RandomAccessFile random = new RandomAccessFile(file, "rw");
		random.seek(chunk.getOffSet());
		random.write(chunk.getBytes());
		random.close();
		System.out.println("setting the last modified in file cache");
		file.setLastModified(chunk.getLastModified());
	}

	public Chunk getChunk(String fileName, int start, int length) throws IOException {
		File file = new File(fileName);
		byte[] bytes = new byte[length];
		RandomAccessFile random = new RandomAccessFile(file, "rw");
		random.read(bytes, start, length);
		random.close();
		return new Chunk(fileName, start, length);
	}

}

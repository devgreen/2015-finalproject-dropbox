package clientServerConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class Chunk {

	private String encodedBytes;
	private String fileName;
	private File file;
	private byte[] bytes;
	private Base64.Encoder encoder;
	private long offSet;

	public Chunk(String fileName, int offSet, int size) {
		this.fileName = fileName;
		bytes = new byte[size];
		encoder = Base64.getEncoder();
		file = new File("C:/Users/Devora/Documents/client/" + fileName);
		this.offSet = offSet;
		try {
			FileInputStream stream = new FileInputStream("C:/Users/Devora/Documents/client/" + fileName);
			stream.read(bytes, (int) offSet, size);
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// encode the bytes to a String
		encodedBytes = encoder.encodeToString(bytes);

	}

	public String toString() {
		return "CHUNK " + fileName + " " + file.length() + " " + file.lastModified() + " " + offSet + " " + encodedBytes;
	}

	public String getFileName() {
		return fileName;
	}

	public long getOffSet() {
		return offSet;
	}

	public byte[] getBytes() {
		return bytes;
	}

}

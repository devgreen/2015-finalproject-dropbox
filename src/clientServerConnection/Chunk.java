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
	private Base64.Decoder decoder;
	private long offSet;

	public Chunk(String fileName, int offSet, int size) {
		this.fileName = fileName;
		bytes = new byte[size + offSet];
		encoder = Base64.getEncoder();
		file = new File(fileName);
		this.offSet = offSet;
		try {
			FileInputStream stream = new FileInputStream(fileName);
			stream.read(bytes, (int) offSet, bytes.length - offSet);
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// encode the bytes to a String
		encodedBytes = encoder.encodeToString(bytes);

	}

	public Chunk(String fileName, int offSet, int size, String encodedBytes) {
		this.fileName = fileName;
		this.offSet = offSet;
		decoder = Base64.getDecoder();
		this.bytes = decoder.decode(encodedBytes);
	}

	@Override
	public String toString() {
		String[] fileNameSplit = fileName.split("/");
		String fileNameWOdirectory = fileNameSplit[fileNameSplit.length - 1];
		return "CHUNK " + fileNameWOdirectory + " " + file.length() + " "
				+ file.lastModified() + " " + offSet + " " + encodedBytes;
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

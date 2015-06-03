package clientServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import messages.ChunkMessage;
import messages.Download;
import messages.FileMessage;
import messages.FilesMessage;
import messages.ListFiles;
import messages.Message;
import messages.Sync;

public class ReaderThread extends Thread {

	private Socket clientSocket;
	private Incoming incoming;
	private PrintWriter writer;

	public ReaderThread(Socket clientSocket, Incoming incoming) throws IOException {

		this.clientSocket = clientSocket;
		this.incoming = incoming;
		writer = new PrintWriter(clientSocket.getOutputStream());
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String strRcvd;
			while ((strRcvd = reader.readLine()) != null) {
				Message msgRcvd;
				try {
					msgRcvd = instantiateMessage(strRcvd);
					incoming.dealWithMessage(msgRcvd);
				} catch (InvalidMessageException e) {
					e.getMessage();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Message instantiateMessage(String strRcvd) throws InvalidMessageException {

		String[] stringSplit = strRcvd.split(" ", 2);
		System.out.println(stringSplit[0]);

		switch (stringSplit[0]) {
		case "LIST":
			return new ListFiles(writer, incoming);
		case "SYNC":
			return new Sync(incoming);
		case "CHUNK":
			return new ChunkMessage(incoming, stringSplit);
		case "DOWNLOAD":
			return new Download(writer, stringSplit);
		case "FILES":
			return new FilesMessage(strRcvd, incoming);
		case "FILE":
			return new FileMessage(strRcvd, incoming);
		}
		throw new InvalidMessageException();

	}

}

package clientServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import messages.ChunkMessage;
import messages.Download;
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
		this.writer = new PrintWriter(clientSocket.getOutputStream());
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String strRcvd;
			while ((strRcvd = reader.readLine()) != null) {
				strRcvd = reader.readLine();
				Message msgRcvd = instantiateMessage(strRcvd);
				incoming.dealWithMessage(msgRcvd);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Message instantiateMessage(String strRcvd) {

		String[] stringSplit = strRcvd.split(" ", 2);

		switch (stringSplit[0]) {
		case "List":
			System.out.println("list message received");
			return new ListFiles(writer, incoming);
		case "Sync":
			return new Sync(incoming);
		case "Chunk":
			return new ChunkMessage(incoming, stringSplit);
		case "Download":
			return new Download(incoming, stringSplit);
		case "FILE":
			System.out.println("entered files case");
			return new FilesMessage(strRcvd, incoming);
		}
		System.out.println("I came, but I didn't match any of your criteria.");
		System.out.println(stringSplit[0]);
		return null;

	}

}

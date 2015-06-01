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
		writer = new PrintWriter(clientSocket.getOutputStream());
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while (true) {
				String strRcvd = reader.readLine();
				Message msgRcvd = instantiateMessage(strRcvd);
				incoming.dealWithMessage(msgRcvd);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Message instantiateMessage(String strRcvd) {
		String[] stringSplit = strRcvd.split(" ");
		switch(stringSplit[0]){
		case "List":
			return new ListFiles(writer, incoming);
		case "Sync":
			return new Sync(incoming);
		case "Chunk":
			return new ChunkMessage(writer, incoming, stringSplit);
		case "Download":
			return new Download();
		}
		return null;
		
	}

}

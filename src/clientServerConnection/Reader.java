package clientServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import messages.Download;
import messages.FileMessage;
import messages.FilesMessage;
import messages.InvalidMessageException;
import messages.ListFiles;
import messages.Message;
import messages.ServerChunkMessage;
import messages.Sync;

public abstract class Reader extends Thread{
	
	private Socket clientSocket;
	protected Incoming incoming;
	protected PrintWriter writer;

	public Reader(Socket clientSocket, Incoming incoming) throws IOException {

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
					System.out.println(e.getMessage());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract Message instantiateMessage(String strRcvd) throws InvalidMessageException;

}

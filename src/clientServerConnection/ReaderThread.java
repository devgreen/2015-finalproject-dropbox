package clientServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import messages.Message;

public class ReaderThread extends Thread {

	private Socket clientSocket;
	private Incoming incoming;

	public ReaderThread(Socket clientSocket, Incoming incoming) {

		this.clientSocket = clientSocket;
		this.incoming = incoming;

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
		//case statement for each string, return Message
		return new Message(clientSocket, incoming);
	}

}

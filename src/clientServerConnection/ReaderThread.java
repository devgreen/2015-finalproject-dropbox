package clientServerConnection;

import java.io.IOException;
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
			ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());
			while (true) {
				Message msgRcvd = (Message) objIn.readObject();
				incoming.dealWithMessage(msgRcvd);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}

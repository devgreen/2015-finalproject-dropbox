package clientServerConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import messages.Message;

public class ReaderThread extends Thread {

	private Socket clientSocket;
	private LinkedBlockingQueue<Message> messages;

	public ReaderThread(Socket clientSocket,
			LinkedBlockingQueue<Message> messages) {

		this.clientSocket = clientSocket;
		this.messages = messages;

	}

	@Override
	public void run() {
		try {
			ObjectInputStream objIn = new ObjectInputStream(
					clientSocket.getInputStream());
			while (true) {
				Message msgRcvd = (Message) objIn.readObject();
				messages.add(msgRcvd);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}

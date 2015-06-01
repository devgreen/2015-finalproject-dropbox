package clientServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

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
				Message msgRcvd = (Message) read.readObject();
				incoming.dealWithMessage(msgRcvd);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}

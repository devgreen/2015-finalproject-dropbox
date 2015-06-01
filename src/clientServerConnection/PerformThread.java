package clientServerConnection;

import java.util.concurrent.LinkedBlockingQueue;

import messages.Message;

public class PerformThread extends Thread {

	private Server server;
	private LinkedBlockingQueue<Message> messages;

	public PerformThread(Server server) {
		this.server = server;
		this.messages = server.getMessages();
	}

	@Override
	public void run() {
				
		while (true) {
			try {
				
				((Message) messages.take()).perform();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

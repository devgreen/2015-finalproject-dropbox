package clientServerConnection;

import java.util.concurrent.LinkedBlockingQueue;

import messages.Message;

public class PerformThread extends Thread {

	private LinkedBlockingQueue<Message> messages;

	public PerformThread(LinkedBlockingQueue<Message> messages) {
		this.messages = messages;
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

package clientServerConnection;

import java.util.concurrent.LinkedBlockingQueue;

import messages.Message;

public class WriterThread extends Thread {

	private LinkedBlockingQueue<Message> messages;

	public WriterThread(LinkedBlockingQueue<Message> messages) {
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

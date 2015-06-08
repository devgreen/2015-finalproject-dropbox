package messages;

import java.io.PrintWriter;
import clientServerConnection.Client;

public class Sync implements Message {

	private Client client;
	private String[] syncCommand;
	private PrintWriter writer;

	public Sync(PrintWriter writer, String[] syncCommand) {
		this.client = (Client) client;
		this.syncCommand = syncCommand;
		this.writer = writer;
	}

	@Override
	public void perform() {
		writer.println("DOWNLOAD " + syncCommand[1]);
		writer.flush();

	}

}

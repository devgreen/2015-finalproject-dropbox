package messages;

import java.io.PrintWriter;

import clientServerConnection.Client;
import clientServerConnection.Incoming;

public class Sync implements Message {

	private String[] syncCommand;
	private PrintWriter writer;
	private Client client;

	public Sync(PrintWriter writer, Incoming incoming, String[] syncCommand) {
		this.syncCommand = syncCommand;
		this.writer = writer;
		this.client = (Client) incoming;
	}

	@Override
	public void perform() {
		// a sync message came in, which means the server now has a new file, so
		// add it to the client's running list of the server's files
		client.addToServersFiles(syncCommand[1]);
		client.addToServerFileInfo(syncCommand[1], Long.parseLong(syncCommand[2]));
		// and now request a download of that file
		writer.println("DOWNLOAD " + syncCommand[1]);
		writer.flush();
	}

}

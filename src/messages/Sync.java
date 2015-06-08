package messages;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import clientServerConnection.Client;
import clientServerConnection.Incoming;
import clientServerConnection.Server;

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
		writer.println("DOWNLOAD" + syncCommand[1]);
		writer.flush();

	}

	@Override
	public void display() {
		// client will never send a sync message
	}
}

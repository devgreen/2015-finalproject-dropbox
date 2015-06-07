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
	private String [] syncCommand;
	

	public Sync(Incoming client, String[] syncCommand) {
		this.client = (Client) client;
		this.syncCommand = syncCommand;
	}

	@Override
	public void perform() {
		client.write("DOWNLOAD" + syncCommand[1]);
	
	}

	@Override
	public void display() {
		// client will never send a sync message
	}
}

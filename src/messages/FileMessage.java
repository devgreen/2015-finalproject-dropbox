package messages;

import clientServerConnection.Client;
import clientServerConnection.Incoming;

public class FileMessage implements Message{

	private String filesListRcvd;
	private Client incoming;

	public FileMessage(String strRcvd, Incoming incoming) {
		this.filesListRcvd = strRcvd;
		this.incoming = (Client) incoming;
	}

	@Override
	public void perform() {

	}

	@Override
	public void display() {
		System.out.println ("client " +filesListRcvd);
		incoming.getArea().append(filesListRcvd + "\n");
	}

}

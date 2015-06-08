package messages;

import clientServerConnection.Client;
import clientServerConnection.Incoming;

public class FileMessage implements Message {

	private String filesListRcvd;
	private Client client;

	public FileMessage(String strRcvd, Incoming incoming) {
		this.filesListRcvd = strRcvd;
		this.client = (Client) incoming;
	}

	@Override
	public void perform() {
		client.getArea().append(filesListRcvd + "\n");
		client.addToServersFiles(filesListRcvd);
	}

}

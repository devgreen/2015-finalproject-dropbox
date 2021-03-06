package messages;

import clientServerConnection.Client;
import clientServerConnection.Incoming;

public class FilesMessage implements Message {

	private String filesListRcvd;
	private Client incoming;

	public FilesMessage(String strRcvd, Incoming incoming) {
		this.filesListRcvd = strRcvd;
		this.incoming = (Client) incoming;
	}

	@Override
	public void perform() {
		incoming.getArea().append(filesListRcvd + "\n");

	}

}

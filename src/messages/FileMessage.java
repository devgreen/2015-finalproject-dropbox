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
		// add the file to the client's list of the server's files - without the
		// word FILE in it.
		String[] temp = filesListRcvd.split(" ");
		//client.addToServersFiles(temp[1]);
		//client.addToServersFiles(temp[2]);
		client.addToServerFileInfo(temp[1], Long.parseLong(temp[2]));
	}

}

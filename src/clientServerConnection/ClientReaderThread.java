package clientServerConnection;

import java.io.IOException;
import java.net.Socket;

import messages.ClientChunkMessage;
import messages.Download;
import messages.FileMessage;
import messages.FilesMessage;
import messages.InvalidMessageException;
import messages.ListFiles;
import messages.Message;
import messages.Sync;

public class ClientReaderThread extends Reader {

	public ClientReaderThread(Socket clientSocket, Incoming incoming) throws IOException {
		super(clientSocket, incoming);
	}

	@Override
	public Message instantiateMessage(String strRcvd) throws InvalidMessageException {
		String[] stringSplit = strRcvd.split(" ");
		System.out.println(strRcvd);

		switch (stringSplit[0]) {
		case "SYNC":
			return new Sync(writer, incoming, stringSplit);
		case "CHUNK":
			return new ClientChunkMessage(incoming, stringSplit);
		case "FILES":
			return new FilesMessage(strRcvd, incoming);
		case "FILE":
			return new FileMessage(strRcvd, incoming);
		}
		throw new InvalidMessageException();
	}

}

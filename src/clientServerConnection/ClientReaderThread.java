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

public class ClientReaderThread extends Reader{

	public ClientReaderThread(Socket clientSocket, Incoming incoming) throws IOException {
		super(clientSocket, incoming);
	}

	@Override
	public Message instantiateMessage(String strRcvd) throws InvalidMessageException {
		String[] stringSplit = strRcvd.split(" ");
		System.out.println(stringSplit[0]);

		switch (stringSplit[0]) {
		case "LIST":
			return new ListFiles(writer, incoming);
		case "SYNC":
			return new Sync(writer, stringSplit);
		case "CHUNK":
			return new ClientChunkMessage(incoming, stringSplit);
		case "DOWNLOAD":
			return new Download(writer, stringSplit);
		case "FILES":
			return new FilesMessage(strRcvd, incoming);
		case "FILE":
			return new FileMessage(strRcvd, incoming);
		}
		throw new InvalidMessageException();
	}

}

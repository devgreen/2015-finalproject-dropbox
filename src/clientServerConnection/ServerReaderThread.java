package clientServerConnection;

import java.io.IOException;
import java.net.Socket;
import messages.Download;
import messages.InvalidMessageException;
import messages.ListFiles;
import messages.Message;
import messages.ServerChunkMessage;

public class ServerReaderThread extends Reader {

	public ServerReaderThread(Socket clientSocket, Incoming incoming)
			throws IOException {
		super(clientSocket, incoming);
	}

	@Override
	public Message instantiateMessage(String strRcvd)
			throws InvalidMessageException {
		String[] stringSplit = strRcvd.split(" ");
		System.out.println(strRcvd);

		switch (stringSplit[0]) {
		case "LIST":
			return new ListFiles(writer, incoming);
		case "CHUNK":
			return new ServerChunkMessage(incoming, stringSplit);
		case "DOWNLOAD":
			return new Download(writer, stringSplit);
		}
		throw new InvalidMessageException();
	}

}

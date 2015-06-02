package messages;

import java.io.PrintWriter;
import clientServerConnection.Incoming;
import clientServerConnection.Server;

import java.util.List;

public class ListFiles implements Message {

	private Server server;
	private List<java.io.File> files;
	private PrintWriter writer;

	public ListFiles(PrintWriter writer, Incoming server) {
		this.writer = writer;
		this.server = (Server) server;
		files = this.server.getFileCache().getFiles();

	}

	@Override
	public void perform() {
		
		writer.println("FILES " + files.size());
		writer.flush();

		for (int i = 0; i < files.size(); i++) {
			writer.println("FILE " + files.get(i).getName() + " "
					+ files.get(i).lastModified() + " " + files.get(i).length());
			writer.flush();

		}

	}

	@Override
	public void display() {
	}

}

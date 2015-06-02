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
		StringBuilder str = new StringBuilder();
		str.append("FILES: ");
		str.append(files.size());
		for (int i = 0; i < files.size(); i++) {
			str.append("FILE ");
			str.append(files.get(i).getName());
			str.append(" ");
			str.append(files.get(i).lastModified());
			str.append(" ");
			str.append(files.get(i).length());

		}
		System.out.println(str.toString());
		writer.println(str.toString());
		writer.flush();
		System.out.println("files written to writer");

	}

	@Override
	public void display() {
	}

}

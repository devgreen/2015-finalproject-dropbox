package messages;

import java.io.PrintWriter;

public class Sync implements Message {

	private String[] syncCommand;
	private PrintWriter writer;

	public Sync(PrintWriter writer, String[] syncCommand) {
		this.syncCommand = syncCommand;
		this.writer = writer;
	}

	@Override
	public void perform() {
		writer.println("DOWNLOAD " + syncCommand[1]);
		writer.flush();
	}

}

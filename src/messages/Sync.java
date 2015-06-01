package messages;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import clientServerConnection.Incoming;
import clientServerConnection.Server;

public class Sync implements Message {

	private Server server;
	private List<Socket> sockets;

	public Sync(Incoming server) {
		this.server = (Server) server;
	}

	@Override
	public void perform() {
		sockets = server.getSockets();
		Iterator<Socket> iter = sockets.iterator();
		while (iter.hasNext()) {
			Socket socket = iter.next();
			try {
				OutputStream out = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(out);
				writer.println("Sync");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				iter.remove();
			}
		}
	}

	@Override
	public void display() {
		// client will never send a sync message
	}

}

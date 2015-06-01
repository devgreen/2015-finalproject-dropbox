package messages;

import java.net.Socket;

import clientServerConnection.Incoming;
import clientServerConnection.Server;

import java.util.List;


public class ListFiles implements Message{
	
	private Socket socketSource;
	private Server server;
	private List<java.io.File> files;
	
	public ListFiles(Socket socketSource, Incoming server){
		this.socketSource = socketSource;
		this.server = (Server) server;
		files = this.server.getFileCache().getFiles();

	}

	@Override
	public void perform( {
	}

	@Override
	public void display() {
		
	}
	
	

}

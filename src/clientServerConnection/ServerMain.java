package clientServerConnection;

import java.io.IOException;

public class ServerMain {

	public static void main(String[] args){
		
		try {
			new Server().connectToClients();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

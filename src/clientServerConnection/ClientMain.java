package clientServerConnection;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientMain {

	public static void main(String args[]){
		
		try {
			Client client = new Client();
			new ClientGUi(client).setVisible(true);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

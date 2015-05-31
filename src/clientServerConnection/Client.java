package clientServerConnection;

import messages.Incoming;
import messages.Message;

public class Client implements Incoming{

	@Override
	public void dealWithMessage(Message message) {
		message.display();
	}
	
	

}

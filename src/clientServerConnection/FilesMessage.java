package clientServerConnection;

import messages.Message;

public class FilesMessage implements Message {

	private String filesListRcvd;
	private Client incoming;
	
	public FilesMessage(String strRcvd, Incoming incoming){
		this.filesListRcvd = strRcvd;
		this.incoming = (Client) incoming;
	}
	
	@Override
	public void perform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void display() {
		incoming.getArea().setText(filesListRcvd);
		//System.out.println(filesListRcvd);
	}

}

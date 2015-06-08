package messages;

public class InvalidMessageException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidMessageException() {
		super("invalid message sent");
	}

}

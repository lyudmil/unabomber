package engine;

public class PlayerMessage {
	
	private String message;
	private int sender;
	private int receiver;
	
	public PlayerMessage(String message, int sender_id, int receiver_id){
		this.message = message;
		this.sender = sender_id;
		this.receiver = receiver_id;
		
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}

package engine;

import java.io.Serializable;

public class PlayerMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	private int sender;
	private int receiver;
	
	public PlayerMessage(String message, int senderId, int receiverId){
		this.message = message;
		this.sender = senderId;
		this.receiver = receiverId;
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

	public String getContent() {
		return message;
	}
}

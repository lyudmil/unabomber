package engine;

public class PlayerMessage {
	
	private String message;
	private int player_id;
	
	public PlayerMessage(String message, int sender_id){
		this.setMessage(message);
		this.setPlayer_id(sender_id);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getPlayer_id() {
		return player_id;
	}

}

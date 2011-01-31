package update;

import java.util.ArrayList;
import java.util.TimerTask;

import ui.UnabomberMap;

import engine.GameStatus;
import engine.PlayerLocation;
import engine.PlayerMessage;

final class UpdateWorldTask extends TimerTask {
	private UnabomberMap activity;
	
	public UpdateWorldTask(UnabomberMap activity) {
		this.activity = activity;
	}

	@Override
	public void run() {
		updatePlayerLocations();
		checkMessages();
		checkGameStatus();
	}

	private void updatePlayerLocations() {
		ArrayList<PlayerLocation> locations = activity.getEngine().getLocations();
		activity.runOnUiThread(new UpdateMap(activity, locations));
	}

	private void checkGameStatus() {
		final GameStatus gameStatus = activity.getEngine().getStatusOfTheGame();
		if (gameStatus.gameEnded()) {
			activity.runOnUiThread(new Runnable() {
				public void run() {
					activity.showDialog(gameStatus.dialogId());
				}
			});
		}
	}

	private void checkMessages() {
		final ArrayList<PlayerMessage> messages = activity.getEngine().getMessages();
		if(activity.getMessages() != null){
			if(activity.getMessages().size() < messages.size()){
				activity.runOnUiThread(new DisplayMessageNotification(activity));
			}
		}
		activity.setMessages(messages);
	}
}
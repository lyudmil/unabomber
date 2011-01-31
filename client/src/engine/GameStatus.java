package engine;

import ui.dialogs.Dialogs;

public enum GameStatus { 
	STARTED {
		@Override
		public int dialogId() {
			return -1;
		}
	},
	WIN {

		@Override
		public int dialogId() {
			return Dialogs.WON_GAME;
		}
	}, 
	LOSE {

		@Override
		public int dialogId() {
			return Dialogs.LOST_GAME;
		}
	}, 
	KILLED {

		@Override
		public int dialogId() {
			return Dialogs.GOT_KILLED;
		}
	}, 
	JAILED {

		@Override
		public int dialogId() {
			return Dialogs.GOT_ARRESTED;
		}
	};

	public abstract int dialogId();

	public boolean gameEnded() {
		return this != GameStatus.STARTED;
	}

	public static GameStatus match(String serverSideStatus) {
		if (serverSideStatus.equals("finished-win")) return WIN;
		if (serverSideStatus.equals("finished-lose")) return LOSE;
		if (serverSideStatus.equals("finished-killed")) return KILLED;
		if (serverSideStatus.equals("finished-jail")) return JAILED;
		
		return STARTED;
	}
}
package engine;

import ui.dialogs.Dialogs;

public enum GameStatus { 
	STARTED {
		@Override
		public int dialogId() {
			return -1;
		}
	},
	FINISHEDWIN {

		@Override
		public int dialogId() {
			return Dialogs.WON_GAME;
		}
	}, 
	FINISHEDLOSE {

		@Override
		public int dialogId() {
			return Dialogs.LOST_GAME;
		}
	}, 
	FINISHEDKILLED {

		@Override
		public int dialogId() {
			return Dialogs.GOT_KILLED;
		}
	}, 
	FINISHEDJAILED {

		@Override
		public int dialogId() {
			return Dialogs.GOT_ARRESTED;
		}
	};

	public abstract int dialogId();
}
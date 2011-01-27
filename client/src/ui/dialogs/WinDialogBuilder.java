package ui.dialogs;

final class WinDialogBuilder extends
		GameEndedMessageDialogBuilder {
	@Override
	protected String getMessage() {
		return "You win! Woo-hoo!";
	}
}
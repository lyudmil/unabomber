package ui.dialogs;

final class ArrestedDialogBuilder extends
		GameEndedMessageDialogBuilder {
	@Override
	protected String getMessage() {
		return "Oops, you got arrested!";
	}
}
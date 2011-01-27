package ui.dialogs;

final class LoseDialogBuilder extends
		GameEndedMessageDialogBuilder {
	@Override
	protected String getMessage() {
		return "I'm sorry, you lose.";
	}
}
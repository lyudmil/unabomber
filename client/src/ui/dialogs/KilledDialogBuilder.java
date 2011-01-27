package ui.dialogs;


final class KilledDialogBuilder extends GameEndedMessageDialogBuilder {

	@Override
	protected String getMessage() {
		return "You've been killed. Sorry!";
	}
}
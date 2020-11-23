package fr.jayrex.calendrier.util;

public enum Messages {
	PREFIX("prefix"),
	PLAYER_ONLY("player only use"),
	NO_PERMISSIONS("no perms"),
	EDIT_INSTRUCTIONS("edit calendar instructions"),
	NOT_A_NUMBER("not a number"),
	NUMBER_NOT_BETWEEN("no number between 1 and 24"),
	EDIT_CALENDAR_INVENTORY_TITLE("edit calendar inventory title"),
	NOT_ENOUGH_SPACE("not enough space"),
	ITEMS_GRANTED("items granted"),
	WRONG_DAY("wrong day"),
	WRONG_MONTH("wrong month"),
	PRESENT_ALREADY_CLAIMED("present already claimed"),
	INVENTORY_SAVED("inventory saved"),
	PRESENT_NAME_GUI("present names in gui"),
	GUI_INVENTORY_TITLE("gui inventory name"),
	INVENTORY_NOT_SAVED("inventory not saved");

	private String key;

	Messages(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return MessageHandler.getMessage(key);
	}

	public String get() {
		return MessageHandler.getMessage(key);
	}

	public String getDateReplaced(int day) {

		String replacement;

		if (day == 1) {
			replacement = "1st";
		} else if (day == 2) {
			replacement = "2nd";
		} else if (day == 3) {
			replacement = "3rd";
		} else {
			replacement = day + "th";
		}
		return MessageHandler.getMessage(key).replace("%day%", replacement);
	}

	public String getRaw() {
		return MessageHandler.getMessageRaw(key);
	}

	public String getReplaced(String expression, String replacement) {
		return MessageHandler.getMessage(key).replace(expression, replacement);
	}

	public String getRawReplaced(String expression, String replacement) {
		return MessageHandler.getMessageRaw(key).replace(expression, replacement);
	}

}

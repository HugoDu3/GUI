package fr.jayrex.calendrier.util;

import fr.jayrex.calendrier.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageHandler {

	private static File file = new File(Main.getInstance().getDataFolder() + File.separator + "messages.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

	/**
	 * @param key Key from the messages.yml
	 * @return Message with prefix
	 */

	public static String getMessage(String key) {

		String message = config.getString(key);
		String prefix = getPrefix();

		if (message != null) {

			message = prefix + ChatColor.translateAlternateColorCodes('&', message);

			return message;
		} else {

			message = prefix + ChatColor.RED + "Error - Missing Message! Please contact an administrator. [" + key + "]";
			return message;

		}
	}

	/**
	 * @param key Key from the messages.yml
	 * @return Message without prefix
	 */

	public static String getMessageRaw(String key) {
		String message = config.getString(key);

		if (message != null) {

			message = ChatColor.translateAlternateColorCodes('&', message);

			return message;
		} else {

			message = getPrefix() + ChatColor.RED + "Error - Missing Message! Please contact an administrator. [" + key + "]";
			return message;

		}

	}

	/**
	 * @return prefix from messages.yml
	 */

	public static String getPrefix() {

		String prefix = config.getString("prefix");

		if (prefix != null) {

			prefix = ChatColor.translateAlternateColorCodes('&', prefix);

			return prefix;
		} else {

			prefix = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "AdventCalendarMain" + ChatColor.GRAY + "]";
			return prefix;

		}

	}

	/**
	 * Sends usage instructions of a command to a commandsender
	 *
	 * @param sender       CommandSender the message is send to.
	 * @param commandusage Command the usage instructions are send for.
	 */

	public static void sendUsage(CommandSender sender, String commandusage) {
		sender.sendMessage(ChatColor.GREEN + "Usage:");
		sender.sendMessage(ChatColor.GRAY + "    \u00BB " + ChatColor.RED + commandusage);
	}

}

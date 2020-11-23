package fr.jayrex.calendrier;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import fr.jayrex.calendrier.commands.CalendrierCommand;
import fr.jayrex.calendrier.commands.EditCalendrierCommand;
import fr.jayrex.calendrier.listener.EntityListener;
import fr.jayrex.calendrier.listener.InventoryListener;

public class Main extends JavaPlugin {


	@Getter
	private static Main instance;

	public static final String PRESENT_HEAD_TEXTURE_URL = "https://textures.minecraft.net/texture/ef79f376f45cc3623f73c63de1427f7dd2acbaf8e5d7844d94d254924ba290";

	public static final String PERM_OPEN = "adventcalendar.open";
	public static final String PERM_EDIT = "adventcalendar.edit";

	@Override
	public void onEnable() {
		instance = this;

		setupFiles();
		getCommand("adventcalendar").setExecutor(new CalendrierCommand());
		getCommand("editadventcalendar").setExecutor(new EditCalendrierCommand());

		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);

	}

	private void setupFiles() {
		this.saveResource("messages.yml", false);
		this.saveResource("adventcalendar.yml", false);
		this.saveResource("openedpresents.yml", false);
	}


}

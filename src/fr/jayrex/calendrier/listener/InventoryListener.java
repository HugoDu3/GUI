package fr.jayrex.calendrier.listener;

import fr.jayrex.calendrier.handler.InventoryHandler;
import fr.jayrex.calendrier.inventory.InventoryMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		if (event.getClickedInventory() == null) {
			return;
		}

		UUID playerId = event.getWhoClicked().getUniqueId();
		InventoryMenu inventoryMenu = InventoryHandler.getInstance().getOpenMenus().get(playerId);

		if (inventoryMenu == null) {
			return;
		}

		inventoryMenu.handleInventoryClick((Player) event.getWhoClicked(), event);

	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {

		UUID playerId = event.getWhoClicked().getUniqueId();
		InventoryMenu inventoryMenu = InventoryHandler.getInstance().getOpenMenus().get(playerId);

		if (inventoryMenu == null) {
			return;
		}

		boolean allowModify = inventoryMenu.isAllowModify();

		if (allowModify) {
			return;
		}

		event.setCancelled(true);

	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {

		UUID playerId = event.getPlayer().getUniqueId();
		InventoryMenu inventoryMenu = InventoryHandler.getInstance().getOpenMenus().remove(playerId);

		if (inventoryMenu == null) {
			return;
		}

		inventoryMenu.handleInventoryClose(Arrays.stream(event.getInventory().getContents()).filter(Objects::nonNull).collect(Collectors.toList()),
				(Player) event.getPlayer());

	}

}
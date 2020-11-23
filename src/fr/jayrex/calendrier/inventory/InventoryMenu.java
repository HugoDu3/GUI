package fr.jayrex.calendrier.inventory;

import fr.jayrex.calendrier.handler.InventoryHandler;
import fr.jayrex.calendrier.util.ItemBuilder;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

@Getter
public class InventoryMenu {

	private BiConsumer<Integer, Player>[] clickHandlers;
	private ItemStack[] items;
	private boolean allowModify;
	private int rows;
	private String title;
	private BiConsumer<List<ItemStack>, Player> closeHandler;

	public InventoryMenu(int rows, String title) {
		this(rows, title, false, null);
	}

	public InventoryMenu(int rows, String title, boolean allowModify) {
		this(rows, title, allowModify, null);
	}

	public InventoryMenu(int rows, String title, boolean allowModify, BiConsumer<List<ItemStack>, Player> closeHandler) {
		clickHandlers = new BiConsumer[rows * 9];
		items = new ItemStack[rows * 9];
		this.allowModify = allowModify;
		this.rows = rows;
		this.title = title;
		this.closeHandler = closeHandler;
	}

	public void setItem(ItemStack item, int index, BiConsumer<Integer, Player> clickHandler) {
		items[index] = item;
		clickHandlers[index] = clickHandler;
	}

	public void fill() {

		ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
				.withDisplayName(ChatColor.RED + "X")
				.build();

		IntStream.range(0, rows * 9)
				.forEach(index -> items[index] = filler);

	}

	public void handleInventoryClick(Player player, InventoryClickEvent event) {

		int index = event.getSlot();

		if (!allowModify) {
			event.setCancelled(true);
		}

		BiConsumer<Integer, Player> clickHandler = clickHandlers[index];

		if (clickHandler == null) {
			return;
		}

		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 5, 1);
		clickHandler.accept(index, player);

	}

	public void handleInventoryClose(List<ItemStack> items, Player player) {
		if (closeHandler == null) {
			return;
		}

		closeHandler.accept(items, player);
	}

	public void openInventory(Player player) {

		Inventory inventory = Bukkit.createInventory(player, rows * 9, title);

		IntStream.range(0, rows * 9)
				.filter(index -> items[index] != null)
				.forEach(index -> inventory.setItem(index, items[index].clone()));

		InventoryHandler.getInstance().getOpenMenus().put(player.getUniqueId(), this);

		player.openInventory(inventory);

	}
	
}

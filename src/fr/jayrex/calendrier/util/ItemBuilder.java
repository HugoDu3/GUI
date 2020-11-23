package fr.jayrex.calendrier.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.jayrex.calendrier.Main;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

	private ItemStack itemstack;

	public ItemBuilder() {
	}

	public ItemBuilder(Material material, Integer amount) {
		this.itemstack = new ItemStack(material, amount);
	}

	public ItemBuilder withLore(String... lore) {
		List<String> loreList = Arrays.asList(lore);

		ItemMeta meta = itemstack.getItemMeta();
		meta.setLore(loreList);
		this.itemstack.setItemMeta(meta);

		return this;
	}

	public ItemBuilder withDisplayName(String displayName) {
		ItemMeta meta = this.itemstack.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

		this.itemstack.setItemMeta(meta);

		return this;
	}

	public ItemStack build() {

		if (this.itemstack == null) {
			this.itemstack = new ItemStack(Material.DIRT);
		}

		return this.itemstack;
	}

	public static ItemStack getPresentHead(String displayname, String... lore) {
		return getSkull(Main.PRESENT_HEAD_TEXTURE_URL, displayname, lore);
	}

	private static ItemStack getSkull(String url, String displayname, String... lore) {

		ItemStack itemstack = new ItemStack(Material.PLAYER_HEAD, 1);

		ItemMeta skullMeta = itemstack.getItemMeta();
		skullMeta.setDisplayName(displayname);
		skullMeta.setLore(Arrays.asList(lore));

		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;

		try {
			profileField = skullMeta.getClass().getDeclaredField("profile");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		profileField.setAccessible(true);

		try {
			profileField.set(skullMeta, profile);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		itemstack.setItemMeta(skullMeta);
		return itemstack;

	}

}

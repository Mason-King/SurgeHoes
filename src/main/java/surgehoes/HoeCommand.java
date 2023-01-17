package surgehoes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import oshi.util.Util;

import java.util.ArrayList;
import java.util.List;

public class HoeCommand implements CommandExecutor {

    private Plugin plugin;

    public HoeCommand(Plugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("harvester").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(args.length == 0) {
            //send help message!
        } else {
            if(args[0].equalsIgnoreCase("give")) {
                if(!p.hasPermission("hoes.give")) return false;
                ItemStack hoe = new ItemStack(Material.WOODEN_HOE);
                ItemMeta im = hoe.getItemMeta();
                im.addEnchant(Enchantment.MENDING, 1, false);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                im.setDisplayName(Utils.color("&f&lHarvester Hoe &7| &o(1)"));
                List<String> lore = new ArrayList<>();
                lore.add(Utils.color("&7Harvest crops to gain stars to upgrade"));
                lore.add(Utils.color("&7your tools"));
                lore.add(Utils.color("&7"));
                lore.add(Utils.color("&7&o(( Right-click to upgrade ))"));
                im.setLore(lore);

                PersistentDataContainer pdc = im.getPersistentDataContainer();
                pdc.set(new NamespacedKey(plugin, "harvester"), PersistentDataType.STRING, "true");
                pdc.set(new NamespacedKey(plugin, "cane"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "stars"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "autosell"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "laser"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "nightfall"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "magmaStomp"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "blackHole"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "arrowRain"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "moneyPouch"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "xpPouch"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "gemPouch"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "keyFinder"), PersistentDataType.INTEGER, 0);
                pdc.set(new NamespacedKey(plugin, "spawnerFinder"), PersistentDataType.INTEGER, 0);

                pdc.set(new NamespacedKey(plugin, "autosellEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "laserEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "nightFallEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "magmaStompEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "blackHoleEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "arrowRainEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "moneyPouchEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "xpPouchEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "gemPouchEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "keyFinderEnabled"), PersistentDataType.STRING, "false");
                pdc.set(new NamespacedKey(plugin, "spawnerFinderEnabled"), PersistentDataType.STRING, "false");


                hoe.setItemMeta(im);

                p.getInventory().addItem(hoe);

                p.sendMessage(Utils.color("&f&lSkySurge &7| You have given yourself a harvester hoe"));
            }
        }
        return false;
    }
}

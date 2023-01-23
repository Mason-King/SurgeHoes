package net.skysurge.Commands;

import net.skysurge.Main;
import net.skysurge.Utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class HoeCommand implements CommandExecutor {

    private Main main;

    public HoeCommand(Main main) {
        this.main = main;
        main.getCommand("hoe").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if(args.length == 0) {
            player.sendMessage(ChatUtils.color("&7Hoe help message"));
        } else if(args[0].equalsIgnoreCase("give")) {
            ItemStack hoe = new ItemStack(Material.WOODEN_HOE);
            ItemMeta itemMeta = hoe.getItemMeta();
            itemMeta.setDisplayName(ChatUtils.color("&f&lHarvester Hoe &7| (1)"));
            List<String> lore = new ArrayList<>();

            double percentage =  ((double) (0 / (int) (Math.pow(1, 2) * 100) * 100));

            String greenBar = "", redBar = "";
            int bars = 50;
            int barPercentage = 100 / bars;

            while(percentage > barPercentage) { // This will execute the code below each time the entered percentage value (for example 60) is greater than the percentage for one bar.
                percentage -= barPercentage;
                greenBar += "|";
                bars -= 1;
            }

            while(bars > 0) {
                redBar += "|";
                bars -= 1;
            }
            lore.add(ChatUtils.color("&7Harvest crops to gain gems."));
            lore.add(ChatUtils.color("&7Use gems to upgrade your hoe"));
            lore.add(ChatUtils.color("&7"));
            lore.add(ChatUtils.color("&f&lProgress &7| "+Math.floor(percentage)+"%"));
            lore.add(ChatUtils.color("&a"+greenBar+"&c"+redBar+""));
            lore.add(ChatUtils.color("&7"));
            lore.add(ChatUtils.color("&7&o(( Right-click to upgrade ))"));

            itemMeta.setLore(lore);
            itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

            //set the data in the pdc
            pdc.set(main.getHarvesterKey(), PersistentDataType.STRING, "true");
            pdc.set(main.getLifetimeCaneKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getLifetimeGemsKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getLevelKey(), PersistentDataType.INTEGER, 1);
            pdc.set(main.getXpKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getLaserKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getNightFallKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getMagmaStompKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getBlackHoleKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getArrowRainKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getMoneyPouchKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getXpPouchKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getGemPouchKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getKeyFinderKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getSpawnerFinderKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getAutoSellKey(), PersistentDataType.INTEGER, 0);
            pdc.set(main.getLaserEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getNightFallEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getMagmaStompEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getBlackHoleEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getArrowRainEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getMoneyPouchEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getXpPouchEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getGemPouchEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getKeyFinderEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getSpawnerFinderEnabledKey(), PersistentDataType.STRING, "false");
            pdc.set(main.getAutoSellEnabledKey(), PersistentDataType.STRING, "false");

            hoe.setItemMeta(itemMeta);

            player.getInventory().addItem(hoe);
        }
        return false;
    }
}

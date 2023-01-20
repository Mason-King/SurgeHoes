package net.skysurge.Gui;

import net.skysurge.Main;
import net.skysurge.Utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

public class HoeGui {

    private Main main;

    public HoeGui(Main main) {
        this.main = main;

    }

    public void makeGui(Player p) {
        Gui gui = new Gui(main);
        Gui.NoobPage mainPage = gui.create(ChatUtils.color("&lHarvester Hoe"), 54);

        mainPage.noClick();
        mainPage.noShift();

        ItemStack hoe = p.getItemInHand();
        PersistentDataContainer pdc = hoe.getItemMeta().getPersistentDataContainer();

        mainPage.i(11, makeItemStack(Material.REDSTONE_BLOCK, "&b&lLaser", main.getLaserKey(), "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + pdc.get(main.getLaserKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 100" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(12, makeItemStack(Material.SCULK_SENSOR, "&b&lNight Fall", main.getNightFallKey(), "&7Chance to collect wither roses", "", "&b&lCurrent Level&7: " + pdc.get(main.getNightFallKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(13, makeItemStack(Material.CRYING_OBSIDIAN, "&b&lBlack Hole", main.getBlackHoleKey(),"&7Spawns a black hole which", "&7Collects crops", "", "&b&lCurrent Level&7: " + pdc.get(main.getBlackHoleKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 100" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))" ));
        mainPage.i(14, makeItemStack(Material.MAGMA_BLOCK, "&b&lMagma Stomp", main.getMagmaStompKey(), "&7Spawns a magma that crushes crops", "", "&b&lCurrent Level&7: " + pdc.get(main.getMagmaStompKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 200" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(15, makeItemStack(Material.ARROW, "&b&lArrow Rain", main.getArrowRainKey(), "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + pdc.get(main.getArrowRainKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(20, makeItemStack(Material.GOLD_BLOCK, "&b&lMoney Pouch", main.getMoneyPouchKey(), "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + pdc.get(main.getMoneyPouchKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(21, makeItemStack(Material.EXPERIENCE_BOTTLE, "&b&lXP Pouch", main.getXpPouchKey(), "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + pdc.get(main.getXpKey(), PersistentDataType.INTEGER), "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(22, makeItemStack(Material.EMERALD_BLOCK, "&b&lGem Pouch", main.getGemPouchKey(), "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + pdc.get(main.getGemPouchKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(23, makeItemStack(Material.TRIPWIRE_HOOK, "&b&lKeyFinder", main.getKeyFinderKey(), "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + pdc.get(main.getKeyFinderKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(24, makeItemStack(Material.SPAWNER, "&b&lSpawnerFinder", main.getSpawnerFinderKey(),"&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + pdc.get(main.getSpawnerFinderKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))" ));
        mainPage.i(31, makeItemStack(Material.DIAMOND_HOE, "&b&lAutosell", main.getAutoSellKey(), "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + pdc.get(main.getAutoSellKey(), PersistentDataType.INTEGER) , "&b&lMax Level&7: 1" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));

        mainPage.i(49, new ItemStack(Material.BOOK));

        mainPage.fill(Material.GRAY_STAINED_GLASS_PANE);

        mainPage.onClick(e -> {
           ItemStack clicked = e.getCurrentItem();
           if(!clicked.hasItemMeta()) return;
           PersistentDataContainer dataContainer = clicked.getItemMeta().getPersistentDataContainer();
           NamespacedKey upgradeKey = new NamespacedKey(main, dataContainer.get(new NamespacedKey(main, "upgrade"), PersistentDataType.STRING));
           int gems = main.getDb().getInt("playerData", "uuid", e.getWhoClicked().getUniqueId().toString(), "gems");
        });

        gui.show(p, 0);

    }

    public ItemStack makeItemStack(Material material, String name, NamespacedKey upgradeKey, String... lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.setDisplayName(ChatUtils.color(name));

        //add lore here
        List<String> loreList = new ArrayList<>();
        for(String s : lore) {
            loreList.add(ChatUtils.color(s));
        }

        itemMeta.setLore(loreList);

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(new NamespacedKey(main, "upgrade"), PersistentDataType.STRING, upgradeKey.getKey().toString());

        stack.setItemMeta(itemMeta);

        return stack;
    }

}

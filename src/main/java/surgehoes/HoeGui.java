package surgehoes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class HoeGui {

    private Plugin plugin;
    private Gui gui;

    private NamespacedKey autosell;
    private NamespacedKey laser;
    private NamespacedKey nightfall;
    private NamespacedKey blackHole;
    private NamespacedKey magmaStomp;
    private NamespacedKey arrowRain;
    private NamespacedKey moneyPouch;
    private NamespacedKey xpPouch;
    private NamespacedKey gemPouch;
    private NamespacedKey keyFinder;
    private NamespacedKey spawnerFinder;

    public HoeGui(Plugin plugin) {
        this.plugin = plugin;

        this.autosell = new NamespacedKey(plugin, "autosell");
        this.laser = new NamespacedKey(plugin, "laser");
        this.nightfall = new NamespacedKey(plugin, "nightFall");
        this.blackHole = new NamespacedKey(plugin, "blackHole");
        this.magmaStomp = new NamespacedKey(plugin, "magmaStomp");
        this.arrowRain = new NamespacedKey(plugin, "arrowRain");
        this.moneyPouch = new NamespacedKey(plugin, "moneyPouch");
        this.xpPouch = new NamespacedKey(plugin, "xpPouch");
        this.gemPouch = new NamespacedKey(plugin, "gemPouch");
        this.keyFinder = new NamespacedKey(plugin, "keyFinder");
        this.spawnerFinder = new NamespacedKey(plugin, "spawnerFinder");
    }
//    ThreadLocalRandom rand = ThreadLocalRandom.current();
//    int chance = (int) Math.round((Double.valueOf(nbt.getInt("tokenFinder") / Double.valueOf(maxLevelToken) * 95)));
//    int lol = rand.nextInt(100);

    public Gui getMainGui() {
        Gui gui = new Gui(plugin);
        Gui.NoobPage mainPage = gui.create(Utils.color("&lHarvester Hoe Menu"), 27);

        mainPage.noClick();
        mainPage.noShift();

        ItemStack stats = new ItemStack(Material.SUGAR_CANE);
        ItemMeta statsM = stats.getItemMeta();
        statsM.setDisplayName(Utils.color("&b&lHarvester Hoe Stats"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.color("&7View all the lifetime"));
        lore.add(Utils.color("&7statistics of your hoe."));
        lore.add(Utils.color("&7"));
        lore.add(Utils.color("&7&o(( Click to open ))"));
        statsM.setLore(lore);
        stats.setItemMeta(statsM);

        ItemStack upgrade = new ItemStack(Material.ANVIL);
        ItemMeta upgradeM = stats.getItemMeta();
        upgradeM.setDisplayName(Utils.color("&b&lHarvester Upgrades"));
        List<String> lore2 = new ArrayList<>();
        lore2.add(Utils.color("&7Upgrade your harvester"));
        lore2.add(Utils.color("&7hoe to gain more items"));
        lore2.add(Utils.color("&7while harvesting."));
        lore2.add(Utils.color("&7"));
        lore2.add(Utils.color("&7&o(( Click to open ))"));
        upgradeM.setLore(lore);
        upgrade.setItemMeta(upgradeM);

        mainPage.i(11, stats);
        mainPage.i(15, upgrade);

        mainPage.fill(Material.GRAY_STAINED_GLASS_PANE);

        mainPage.onClick(e -> {
            Player p = (Player) e.getWhoClicked();
            if(e.getSlot() == 11) {
                //stats
            } else if(e.getSlot() == 15) {
                getUpgradeGui(p).show(p, 0);
            }
        });

        return gui;
    }

    public Gui getUpgradeGui(Player p) {
        Gui gui = new Gui(plugin);
        Gui.NoobPage mainPage = gui.create(Utils.color("&lHoe Upgrades"), 45);
        mainPage.noShift();
        mainPage.noClick();
        ItemStack hoe = p.getItemInHand();
        PersistentDataContainer dataContainer = hoe.getItemMeta().getPersistentDataContainer();

        mainPage.i(11, makeStack(Material.REDSTONE_BLOCK, "&b&lLaser", "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + dataContainer.get(laser, PersistentDataType.INTEGER) , "&b&lMax Level&7: 100" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(12, makeStack(Material.SCULK_SENSOR, "&b&lNight fall", "&7Chance to collect wither roses", "", "&b&lCurrent Level&7: " + dataContainer.get(nightfall, PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(13, makeStack(Material.CRYING_OBSIDIAN, "&b&lBlack Hole", "&7Spawns a black hole which", "&7Collects crops", "", "&b&lCurrent Level&7: " + dataContainer.get(blackHole, PersistentDataType.INTEGER) , "&b&lMax Level&7: 100" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(14, makeStack(Material.MAGMA_BLOCK, "&b&lMagma Stomp", "&7Spawns a magma that crushes crops", "", "&b&lCurrent Level&7: " + dataContainer.get(magmaStomp, PersistentDataType.INTEGER) , "&b&lMax Level&7: 200" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(15, makeStack(Material.ARROW, "&b&lArrow Rain", "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + dataContainer.get(arrowRain, PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(20, makeStack(Material.GOLD_BLOCK, "&b&lMoney Pouch", "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + dataContainer.get(moneyPouch, PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(21, makeStack(Material.EXPERIENCE_BOTTLE, "&b&lXP Pouch", "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + dataContainer.get(xpPouch, PersistentDataType.INTEGER), "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(22, makeStack(Material.EMERALD, "&b&lGem Pouch", "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + dataContainer.get(gemPouch, PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(23, makeStack(Material.TRIPWIRE_HOOK, "&b&lKey Finder", "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + dataContainer.get(keyFinder, PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(24, makeStack(Material.SPAWNER, "&b&lSpawner Finder", "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + dataContainer.get(spawnerFinder, PersistentDataType.INTEGER) , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(31, makeStack(Material.DIAMOND_HOE, "&b&lAutosell", "&7Chance to destroy cane in a row", "", "&b&lCurrent Level&7: " + dataContainer.get(autosell, PersistentDataType.INTEGER) , "&b&lMax Level&7: 1" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));

        mainPage.fill(Material.GRAY_STAINED_GLASS_PANE);

        //handles clicks and shit
        mainPage.onClick(e -> {
           int slot = e.getSlot();
           if(slot == 11) {
               //laser
           }
        });

        return gui;
    }

    public ItemStack makeStack(Material mat, String name, String... lore) {
        ItemStack stack = new ItemStack(mat);

        ItemMeta im = stack.getItemMeta();
        im.setDisplayName(Utils.color(name));

        List<String> loreList = new ArrayList<>();
        for(String s : lore){
            loreList.add(Utils.color(s));
        }
        im.setLore(loreList);
        stack.setItemMeta(im);

        return stack;
    }

}

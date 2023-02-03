package net.skysurge.Gui;

import net.skysurge.Main;
import net.skysurge.Utils.ChatUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.StringUtil;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

public class HoeGui {

    private Main main;

    private String[] upgrades = {"arrow rain", "magma stomp", "xp pouch", "autosell", "laser", "key finder", "spawner finder", "money pouch", "gem pouch", "night fall"};
    private int arrowrain = 0, magmastomp = 15, xppouch = 20,blackhole = 30,autosell = 40,laser = 45,keyfinder = 50,spawnerfinder = 60, moneypouch = 70,gempouch = 80,nightfall = 100;
    private int arrowrainMax = 500, magmastompMax = 200, xppouchMax = 500,autosellMax = 1,laserMax = 100,keyfinderMax = 500,spawnerfinderMax = 500, moneypouchMax = 500,gempouchMax = 500,nightfallMax = 200;
    private int arrowrainPrice = 10, magmastompPrice = 15, xppouchPrice = 20,autosellPrice = 40,laserPrice = 45,keyfinderPrice = 50,spawnerfinderPrice = 60, moneypouchPrice = 70,gempouchPrice = 80,nightfallPrice = 100;


    public HoeGui(Main main) {
        this.main = main;

    }

    public void makeGui(Player p) {
        Gui gui = new Gui(main);
        Gui.NoobPage mainPage = gui.create(ChatUtils.color("&lHarvester Hoe"), 36);

        mainPage.noClick();
        mainPage.noShift();

        ItemStack hoe = p.getItemInHand();
        PersistentDataContainer pdc = hoe.getItemMeta().getPersistentDataContainer();

        fillGui(mainPage, pdc);

        mainPage.onClick(e -> {
           ItemStack clicked = e.getCurrentItem();
           ItemStack hoeItem = e.getWhoClicked().getItemInHand();
           if(clicked == null ||    !clicked.hasItemMeta()) return;
           PersistentDataContainer dataContainer = clicked.getItemMeta().getPersistentDataContainer();
           ItemMeta im = hoe.getItemMeta();
           PersistentDataContainer hoeContainer = im.getPersistentDataContainer();
           NamespacedKey upgradeKey = new NamespacedKey(main, dataContainer.get(new NamespacedKey(main, "upgrade"), PersistentDataType.STRING));
           int gems = this.main.getGemUtils().getGems(p);
           int level = hoeContainer.getOrDefault(main.getLevelKey(), PersistentDataType.INTEGER, 1);

           try {
               int requiredLevel = (int) this.getClass().getDeclaredField(upgradeKey.getKey()).get(this);
               //Just commenting out the level check for testing <- will need to test later.
               //if(level >= requiredLevel) {
                   //they have the right level
                   int price = (int) this.getClass().getDeclaredField(upgradeKey.getKey() + "Price").get(this) * (int) Math.pow(2, level -1);
               System.out.println(price);
                   if(gems >= price) {
                       if((hoeContainer.get(upgradeKey, PersistentDataType.INTEGER) + 1) > (int) this.getClass().getDeclaredField(upgradeKey.getKey() + "Max").get(this)) {
                           p.sendMessage(ChatUtils.color("&f&lSkySurge &7| This enchant is already max level!"));
                            return;
                       }
                       main.getGemUtils().removeGems(p, price);

                       int newLevel = hoeContainer.get(upgradeKey, PersistentDataType.INTEGER) + 1;
                       hoeContainer.set(upgradeKey, PersistentDataType.INTEGER, newLevel);
                       im.setLore(generateLore(hoe));
                       hoe.setItemMeta(im);

                       mainPage.clear();
                       fillGui(mainPage, hoe.getItemMeta().getPersistentDataContainer());

                       p.sendMessage(ChatUtils.color("&f&lSkySurge &7| You have purchased 1 level of " + StringUtils.capitalize(upgradeKey.getKey())));
                   } else {
                       p.sendMessage(ChatUtils.color("&f&lSKySurge &7| Sorry! You do not have enough gems to purchase this enchant!"));
                   }
//               } else {
//                   p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Sorry! Your harvester hoe is not a high enough level for this!"));
//                   return;
//               }
           } catch (NoSuchFieldException noSuchFieldException) {
               noSuchFieldException.printStackTrace();
           } catch (IllegalAccessException illegalAccessException) {
               illegalAccessException.printStackTrace();
           }
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

    public Gui.NoobPage fillGui(Gui.NoobPage mainPage, PersistentDataContainer pdc) {
        int laser = pdc.get(main.getLaserKey(), PersistentDataType.INTEGER), nightfall = pdc.get(main.getNightFallKey(), PersistentDataType.INTEGER), autosell = pdc.get(main.getAutoSellKey(), PersistentDataType.INTEGER), magmastomp = pdc.get(main.getMagmaStompKey(), PersistentDataType.INTEGER), arrowrain = pdc.get(main.getArrowRainKey(), PersistentDataType.INTEGER), moneypouch = pdc.get(main.getMoneyPouchKey(), PersistentDataType.INTEGER), xppouch = pdc.get(main.getXpPouchKey(), PersistentDataType.INTEGER), gempouch =pdc.get(main.getGemPouchKey(), PersistentDataType.INTEGER), keyfinder = pdc.get(main.getKeyFinderKey(), PersistentDataType.INTEGER), spawnerfinder = pdc.get(main.getSpawnerFinderKey(), PersistentDataType.INTEGER);
        mainPage.i(11, makeItemStack(Material.REDSTONE_BLOCK, "&b&lLaser", main.getLaserKey(), "&7Chance to destroy cane in a row", "", "&b&lUpgrade Price&7: " + laserPrice * (int) Math.pow(2, laser + 1),"&b&lCurrent Level&7: " + laser , "&b&lMax Level&7: 100" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(12, makeItemStack(Material.SCULK_SENSOR, "&b&lNight Fall", main.getNightFallKey(), "&7Chance to collect wither roses", "","&b&lUpgrade Price&7: " + nightfallPrice * (int) Math.pow(2, nightfall + 1),"&b&lCurrent Level&7: " + nightfall  , "&b&lMax Level&7: 200" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(13, makeItemStack(Material.DIAMOND_HOE, "&b&lAutosell", main.getAutoSellKey(), "&7Chance to destroy cane in a row", "", "&b&lUpgrade Price&7: " + autosellPrice * (int) Math.pow(2, autosell + 1),"&b&lCurrent Level&7: " + autosell  , "&b&lMax Level&7: 1" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(14, makeItemStack(Material.MAGMA_BLOCK, "&b&lMagma Stomp", main.getMagmaStompKey(), "&7Spawns a magma that crushes crops", "","&b&lUpgrade Price&7: " + magmastompPrice * (int) Math.pow(2, magmastomp + 1),"&b&lCurrent Level&7: " + magmastomp , "&b&lMax Level&7: 200" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(15, makeItemStack(Material.ARROW, "&b&lArrow Rain", main.getArrowRainKey(), "&7Chance to destroy cane in a row", "", "&b&lUpgrade Price&7: " +  arrowrainPrice * (int) Math.pow(2, arrowrain + 1),"&b&lCurrent Level&7: " + arrowrain , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(20, makeItemStack(Material.GOLD_BLOCK, "&b&lMoney Pouch", main.getMoneyPouchKey(), "&7Chance to destroy cane in a row", "","&b&lUpgrade Price&7: " + moneypouchPrice * (int) Math.pow(2, moneypouch + 1),"&b&lCurrent Level&7: " + moneypouch , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(21, makeItemStack(Material.EXPERIENCE_BOTTLE, "&b&lXP Pouch", main.getXpPouchKey(), "&7Chance to destroy cane in a row", "","&b&lUpgrade Price&7: " + xppouchPrice * (int) Math.pow(2, xppouch + 1),"&b&lCurrent Level&7: " + xppouch , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(22, makeItemStack(Material.EMERALD_BLOCK, "&b&lGem Pouch", main.getGemPouchKey(), "&7Chance to destroy cane in a row", "","&b&lUpgrade Price&7: " + gempouchPrice * (int) Math.pow(2, gempouch + 1),"&b&lCurrent Level&7: " + gempouch , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(23, makeItemStack(Material.TRIPWIRE_HOOK, "&b&lKey Finder", main.getKeyFinderKey(), "&7Chance to destroy cane in a row", "","&b&lUpgrade Price&7: " + keyfinderPrice * (int) Math.pow(2, keyfinder + 1),"&b&lCurrent Level&7: " + keyfinder, "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))"));
        mainPage.i(24, makeItemStack(Material.SPAWNER, "&b&lSpawner Finder", main.getSpawnerFinderKey(),"&7Chance to destroy cane in a row", "", "&b&lUpgrade Price&7: " + spawnerfinder * (int) Math.pow(2, spawnerfinder + 1),"&b&lCurrent Level&7: " + spawnerfinder , "&b&lMax Level&7: 500" , "", "&7&o(( Left-click to upgrade))", "&7&o(( Right-click to disable ))" ));


        mainPage.fill(Material.GRAY_STAINED_GLASS_PANE);

        return mainPage;
    }

    public List<String> generateLore(ItemStack stack) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatUtils.color("&7Harvest crops to gain gems."));
        lore.add(ChatUtils.color("&7Use gems to upgrade your hoe"));
        lore.add(ChatUtils.color("&7"));

        PersistentDataContainer dc = stack.getItemMeta().getPersistentDataContainer();
        int xpNeeded = (int) (Math.pow(dc.get(main.getLevelKey(), PersistentDataType.INTEGER), 2) * 100);
        double percentage =  ((double) (dc.get(main.getXpKey(), PersistentDataType.INTEGER)) / xpNeeded * 100);

        lore.add(ChatUtils.color("&f&lProgress &7| "+Math.floor(percentage)+"%"));

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

        lore.add(ChatUtils.color("&a"+greenBar+"&c"+redBar+""));
        lore.add(ChatUtils.color("&7"));

        for(String s : upgrades) {
            NamespacedKey upgradeKey = new NamespacedKey(main, s.replace(" ", ""));
            int level = dc.get(upgradeKey, PersistentDataType.INTEGER);
            if(level > 0) {
                lore.add(ChatUtils.color("&7&l| &b" + StringUtils.capitalize(s) + " &7(" + level + ")"));
            }

        }
        lore.add("");
        lore.add(ChatUtils.color("&7&o(( Right-click to upgrade ))"));

        return lore;
    }

}

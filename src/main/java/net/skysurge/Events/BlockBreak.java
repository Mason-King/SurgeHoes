package net.skysurge.Events;

import net.skysurge.Gui.HoeGui;
import net.skysurge.Main;
import net.skysurge.Utils.ChatUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockBreak implements Listener {

    Main main;

    public BlockBreak(Main main) {
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }
    private String[] upgrades = {"arrow rain", "magma stomp", "xp pouch", "autosell", "laser", "key finder", "spawner finder", "money pouch", "gem pouch", "night fall"};

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.SUGAR_CANE)) {
                ItemStack hoe = e.getItem();
                //Check if it has the harvester hoe key in the PDC
                 if(hoe.getItemMeta().getPersistentDataContainer().get(main.getHarvesterKey(), PersistentDataType.STRING).equals("true")) {
                    e.setCancelled(true);
                    //if they click the bottom block, no need to go further :)
                    if(isBottom(e.getClickedBlock())) return;
                     List<Block> canes = getBlocks(e.getClickedBlock());
                     for(Block b : canes) {
                         b.setType(Material.AIR);
                     }
                     if(hoe.getItemMeta().getPersistentDataContainer().has(main.getAutoSellKey(), PersistentDataType.INTEGER) && hoe.getItemMeta().getPersistentDataContainer().get(main.getAutoSellKey(), PersistentDataType.INTEGER) == 1) {
                        //autosell enabled
                        int sell = (main.getTask().toSell.containsKey(e.getPlayer().getUniqueId())) ? main.getTask().toSell.get(e.getPlayer().getUniqueId()) + canes.size() : canes.size();
                        main.getTask().toSell.remove(e.getPlayer().getUniqueId());
                        main.getTask().toSell.put(e.getPlayer().getUniqueId(), sell);
                        System.out.println(sell);
                    } else {
                        //auto sell disabled
                        e.getPlayer().getInventory().addItem(new ItemStack(Material.SUGAR_CANE, canes.size()));
                    }

                     main.getCaptchaGui().makeGui(e.getPlayer());

                    ItemMeta im = hoe.getItemMeta();
                    PersistentDataContainer pdc = im.getPersistentDataContainer();
                    int level = pdc.getOrDefault(main.getLevelKey(), PersistentDataType.INTEGER, 1);
                    int xp = pdc.getOrDefault(main.getXpKey(), PersistentDataType.INTEGER, 0);

                    xp += 5;
                    int xpNeeded = (int) (Math.pow(level, 2) * 1000);
                    if(xp >= xpNeeded) {
                        //level up!
                        level++;
                        xp = 0;
                        im.setDisplayName(ChatUtils.color("&f&lHarvester Hoe &7| ("+level+")"));

                        pdc.set(main.getLevelKey(), PersistentDataType.INTEGER, level);

                        e.getPlayer().sendTitle(ChatUtils.color("&b&lHoe Upgraded!"), ChatUtils.color("&7Your hoe has been upgraded to level " + level), 10, 50, 20);
                    }

                    pdc.set(main.getXpKey(), PersistentDataType.INTEGER, xp);
                    im.setLore(generateLore(hoe, xpNeeded));
                    hoe.setItemMeta(im);

                    Random rand = new Random();
                    int chance = rand.nextInt(100);

                    //makes a 5% chance
                    if(chance < 5) {
                        if(this.main.getDb().exists("playerData", "uuid", e.getPlayer().getUniqueId().toString())) {
                            this.main.getDb().execute("REPLACE into playerData (uuid, gems) VALUES ('"+ e.getPlayer().getUniqueId().toString() + "', '" + (this.main.getGemUtils().getGems(e.getPlayer()) + 1) + "')");
                            e.getPlayer().sendMessage(ChatUtils.color("&f&lSkySurge &7| You have received 1 gem from harvesting sugar cane."));
                        } else return;
                    }
                } else return;
            }
        }
        //Just used to open the gui <3
        else if(e.getAction().equals(Action.RIGHT_CLICK_AIR) && e.getItem().getItemMeta().getPersistentDataContainer().has(main.getHarvesterKey(), PersistentDataType.STRING)) {
            main.getHoeGui().makeGui(e.getPlayer());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!main.getDb().exists("playerData", "uuid", p.getUniqueId().toString()) || !p.hasPlayedBefore()) {
            System.out.println("Creating new player data!");
            main.getDb().execute("INSERT INTO playerData (uuid, gems) VALUES ('"+p.getUniqueId()+"', '0')");

        }
    }

    public boolean isBottom(Block b) {
        Block bBelow = b.getRelative(BlockFace.DOWN);
        if(b.getType().equals(bBelow.getType())) {
            return false;
        }

        return true;
    }

    public List<Block> getBlocks(Block b) {
        List<Block> blocks = new ArrayList<>();
        blocks.add(b);
        Location loc = b.getLocation().clone().add(0, 1, 0);
        while(loc.getBlock().getType().equals(Material.SUGAR_CANE)) {
            blocks.add(loc.getBlock());
            loc.add(0,1,0);
        }
        return blocks;
    }

    public List<String> generateLore(ItemStack stack, int xpNeeded) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatUtils.color("&7Harvest crops to gain gems."));
        lore.add(ChatUtils.color("&7Use gems to upgrade your hoe"));
        lore.add(ChatUtils.color("&7"));

        PersistentDataContainer dc = stack.getItemMeta().getPersistentDataContainer();
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

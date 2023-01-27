package net.skysurge.Events;

import net.skysurge.Main;
import net.skysurge.Utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MoneyPouch implements Listener {

    Main main;

    public MoneyPouch(Main main) {
        this.main = main;

        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void moneyPouch(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.SUGAR_CANE)) {
                ItemStack hoe = e.getItem();
                //Check if it has the harvester hoe key in the PDC
                if (hoe.getItemMeta().getPersistentDataContainer().get(main.getHarvesterKey(), PersistentDataType.STRING).equals("true")) {
                    int moneyPouch = hoe.getItemMeta().getPersistentDataContainer().get(main.getMoneyPouchKey(), PersistentDataType.INTEGER);
                    if (moneyPouch == 0) return;


                    int chance = ThreadLocalRandom.current().nextInt(100) + 1;

                    if (chance <= moneyPouch * 0.75) {
                        int minXp = 1000 + (moneyPouch * 400);
                        int maxXp = 5000 + (moneyPouch * 400);
                        int range = maxXp - minXp;
                        int randomizedXP = (int) (Math.random() * range) + minXp;

                        e.getPlayer().sendMessage(ChatUtils.color("&f&lSkySurge &7| You have received &b&l$" + randomizedXP + " &7from &b&lMoney Pouch"));
                        main.getEcon().depositPlayer(e.getPlayer(), randomizedXP);
                   }

                }
            }
        }
    }

    public boolean hasOnTop(Block b) {
        Block bBelow = b.getRelative(BlockFace.UP);
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

}
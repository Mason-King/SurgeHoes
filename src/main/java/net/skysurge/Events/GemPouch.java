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

public class GemPouch implements Listener {

    Main main;

    public GemPouch(Main main) {
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
                    int gemPouch = hoe.getItemMeta().getPersistentDataContainer().get(main.getGemPouchKey(), PersistentDataType.INTEGER);
                    if (gemPouch == 0) return;


                    int chance = ThreadLocalRandom.current().nextInt(100) + 1;

                    if (chance <= gemPouch * 0.75) {
                        int minXp = 100 + (gemPouch * 10);
                        int maxXp = 500 + (gemPouch * 10);
                        int range = maxXp - minXp;
                        int randomizedGems = (int) (Math.random() * range) + minXp;

                        e.getPlayer().sendMessage(ChatUtils.color("&f&lSkySurge &7| You have received &b&l" + randomizedGems + " &7 gems from &b&lGem Pouch"));
                        main.getGemUtils().addGems(e.getPlayer(), randomizedGems);
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
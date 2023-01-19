package net.skysurge.Events;

import net.skysurge.Gui.HoeGui;
import net.skysurge.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
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

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.SUGAR_CANE)) {
                ItemStack hoe = e.getItem();
                //Check if it has the harvester hoe key in the PDC
                if(hoe.getItemMeta().getPersistentDataContainer().get(main.getHarvesterKey(), PersistentDataType.STRING) == "true") {
                    e.setCancelled(true);
                    //if they click the bottom block, no need to go further :)
                    if(isBottom(e.getClickedBlock())) return;
                    if(hoe.getItemMeta().getPersistentDataContainer().has(main.getAutoSellKey(), PersistentDataType.STRING) && hoe.getItemMeta().getPersistentDataContainer().get(main.getAutoSellKey(), PersistentDataType.STRING) == "true") {
                        //autosell enabled
                    } else {
                        //auto sell disabled
                        List<Block> canes = getBlocks(e.getClickedBlock());
                        for(Block b : canes) {
                            b.setType(Material.AIR);
                        }
                        e.getPlayer().getInventory().addItem(new ItemStack(Material.SUGAR_CANE, canes.size()));
                    }

                    Random rand = new Random();
                    int chance = rand.nextInt(100);

                    //makes a 10% chance
                    System.out.println(chance + " Chance!");
                    if(chance < 100) {
                        System.out.println(this.main.getDb().exists("playerData", "uuid", e.getPlayer().getUniqueId().toString()));
                    }
                } else return;
            }
        }
        //Just used to open the gui <3
        else if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            main.getHoeGui().makeGui(e.getPlayer());
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
}

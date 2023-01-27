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

public class Laser implements Listener {

    Main main;

    public Laser(Main main) {
        this.main = main;

        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void laser(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.SUGAR_CANE)) {
                ItemStack hoe = e.getItem();
                //Check if it has the harvester hoe key in the PDC
                if (hoe.getItemMeta().getPersistentDataContainer().get(main.getHarvesterKey(), PersistentDataType.STRING).equals("true")) {
                    int laser = hoe.getItemMeta().getPersistentDataContainer().get(main.getLaserKey(), PersistentDataType.INTEGER);
                    if (laser == 0) return;


                    int chance = ThreadLocalRandom.current().nextInt(100) + 1;

                    if (chance <= laser * 0.75) {
                        Location start = e.getClickedBlock().getLocation();
                        if(isBottom(start.getBlock())) return;
                        while(start.add(e.getPlayer().getFacing().getModX(), 0, e.getPlayer().getFacing().getModZ()).getBlock().getType().equals(Material.SUGAR_CANE)) {
                            List<Block> blocks = getBlocks(start.getBlock());
                            for(Block b : blocks) {
                                b.setType(Material.AIR);
                            }
                            if(hoe.getItemMeta().getPersistentDataContainer().has(main.getAutoSellKey(), PersistentDataType.INTEGER) && hoe.getItemMeta().getPersistentDataContainer().get(main.getAutoSellKey(), PersistentDataType.INTEGER) == 1) {
                                //autosell enabled
                                int sell = (main.getTask().toSell.containsKey(e.getPlayer().getUniqueId())) ? main.getTask().toSell.get(e.getPlayer().getUniqueId()) + blocks.size() : blocks.size();
                                main.getTask().toSell.remove(e.getPlayer().getUniqueId());
                                main.getTask().toSell.put(e.getPlayer().getUniqueId(), sell);
                            } else {
                                e.getPlayer().getInventory().addItem(new ItemStack(Material.SUGAR_CANE, blocks.size()));
                            }
                        }
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
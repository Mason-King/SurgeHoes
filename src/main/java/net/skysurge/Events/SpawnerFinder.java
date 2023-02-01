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
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnerFinder implements Listener {

    Main main;

    public SpawnerFinder(Main main) {
        this.main = main;

        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void spawnerFinder(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.SUGAR_CANE)) {
                ItemStack hoe = e.getItem();
                //Check if it has the harvester hoe key in the PDC
                if (hoe.getItemMeta().getPersistentDataContainer().get(main.getHarvesterKey(), PersistentDataType.STRING).equals("true")) {
                    int spawnerFinder = hoe.getItemMeta().getPersistentDataContainer().get(main.getSpawnerFinderKey(), PersistentDataType.INTEGER);
                    if (spawnerFinder == 0) return;

                    int chance = ThreadLocalRandom.current().nextInt(100) + 1;

                    if (chance <= spawnerFinder * 0.75) {
                        //run the command to give a spawner crate thingy
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
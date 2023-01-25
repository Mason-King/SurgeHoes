package net.skysurge.Events;

import net.skysurge.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MagmaStomp implements Listener {

    Main main;

    public MagmaStomp(Main main) {
        this.main = main;

        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void arrowRain(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.SUGAR_CANE)) {
                ItemStack hoe = e.getItem();
                //Check if it has the harvester hoe key in the PDC
                if (hoe.getItemMeta().getPersistentDataContainer().get(main.getHarvesterKey(), PersistentDataType.STRING).equals("true")) {
                    int magmastomp = hoe.getItemMeta().getPersistentDataContainer().get(main.getMagmaStompKey(), PersistentDataType.INTEGER);
                    if (magmastomp == 0) return;

                    int chance = ThreadLocalRandom.current().nextInt(100) + 1;

                    //if (chance <= magmastomp * 1.75) {
                    System.out.println("here");
                        MagmaCube magmaCube = (MagmaCube) e.getPlayer().getLocation().getWorld().spawnEntity(e.getPlayer().getLocation().add(0, 5, 0), EntityType.MAGMA_CUBE);
                        magmaCube.setSize(5);
                        // Create a list to keep track of the locations the magma cube moves to
                        List<Location> moveLocations = new ArrayList<>();
                    System.out.println("here2");
                        // Register the pathfinder event listener

                        // Make the magma cube pathfind randomly for 10 seconds
                        // Print out the locations the magma cube moved to
                        for (Location moveLocation : moveLocations) {
                            System.out.println("Magma cube moved to: " + moveLocation);
                        }
                    //}

                }
            }
        }
    }

    //IDK what event to use

    @EventHandler
    public void onMove(EntityTargetEvent     e){
        System.out.println("here");
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
package net.skysurge.Events;

import net.skysurge.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ArrowRain implements Listener {

    Main main;

    public ArrowRain(Main main) {
        this.main = main;

        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void arrowRain(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.SUGAR_CANE)) {
                ItemStack hoe = e.getItem();
                //Check if it has the harvester hoe key in the PDC
                if (hoe.getItemMeta().getPersistentDataContainer().get(main.getHarvesterKey(), PersistentDataType.STRING).equals("true")) {
                    int arrowrain = hoe.getItemMeta().getPersistentDataContainer().get(main.getArrowRainKey(), PersistentDataType.INTEGER);
                    if(arrowrain == 0) return;

                    int chance = ThreadLocalRandom.current().nextInt(100) + 1;

//                    if(chance <= arrowrain * 1.75) {
                        spawnArrows(e.getPlayer());
                    //}
                }
            }
        }
    }

    private final int NUM_ARROWS = 15;
    private final int RADIUS = 10;

    public void spawnArrows(Player player) {
        Location playerLoc = player.getLocation();
        Random rand = new Random();
        List<Location> arrowLocations = new ArrayList<>();

        for (int i = 0; i < NUM_ARROWS; i++) {
            double x = playerLoc.getX() + (rand.nextDouble() * RADIUS * 2) - RADIUS;
            double z = playerLoc.getZ() + (rand.nextDouble() * RADIUS * 2) - RADIUS;
            Location arrowLoc = new Location(playerLoc.getWorld(), x, playerLoc.getY(), z);

            Arrow arrow = playerLoc.getWorld().spawnArrow(arrowLoc, playerLoc.getDirection(), 0, 0);
            arrowLocations.add(arrow.getLocation());
        }

        for(Location loc : arrowLocations) {
            Block b = loc.add(0,1,0).getBlock();
            if(loc.add(0,1,0).getBlock().getType().equals(Material.SUGAR_CANE)) {
                if(hasOnTop(b)) return;
                if(player.getInventory().getItemInHand().getItemMeta().getPersistentDataContainer().has(main.getAutoSellKey(), PersistentDataType.STRING) && player.getInventory().getItemInHand().getItemMeta().getPersistentDataContainer().get(main.getAutoSellKey(), PersistentDataType.STRING) == "true") {
                    //autosell enabled
                } else {
                    //auto sell disabled
                    List<Block> canes = getBlocks(b);
                    for(Block bl : canes) {
                        bl.setType(Material.AIR);
                    }
                    player.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, canes.size()));
                }
            }
        }

        for (Entity entity : playerLoc.getWorld().getEntities()) {
            // Check if the entity is an arrow
            if (entity instanceof Arrow) {
                // Check if the arrow is within the range of the player
                if (playerLoc.distance(entity.getLocation()) <= RADIUS) {
                    entity.remove();
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
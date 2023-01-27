package net.skysurge.Events;

import io.papermc.paper.event.entity.EntityMoveEvent;
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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MagmaStomp implements Listener {

    Main main;
    private Map<UUID, List<Location>> locations = new HashMap<>();

    public MagmaStomp(Main main) {
        this.main = main;

        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void magmaStomp(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.SUGAR_CANE)) {
                ItemStack hoe = e.getItem();
                //Check if it has the harvester hoe key in the PDC
                if (hoe.getItemMeta().getPersistentDataContainer().get(main.getHarvesterKey(), PersistentDataType.STRING).equals("true")) {
                    int magmastomp = hoe.getItemMeta().getPersistentDataContainer().get(main.getMagmaStompKey(), PersistentDataType.INTEGER);
                    if (magmastomp == 0) return;

                    int chance = ThreadLocalRandom.current().nextInt(100) + 1;

                    if (chance <= magmastomp * 0.75) {
                        MagmaCube magmaCube = (MagmaCube) e.getPlayer().getLocation().getWorld().spawnEntity(e.getPlayer().getLocation().add(0, 5, 0), EntityType.MAGMA_CUBE);
                        magmaCube.setSize(5);
                        UUID u = UUID.randomUUID();
                        magmaCube.setMetadata("id", new FixedMetadataValue(main, u.toString()));

                        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                            @Override
                            public void run() {
                                magmaCube.remove();
                                for(Location l : locations.get(u)) {
                                    if(l.getBlock().getType().equals(Material.SUGAR_CANE)) {
                                        List<Block> blocks = getBlocks(l.getBlock());
                                        for(Block b : blocks) {
                                            if(isBottom(b)) return;
                                            b.setType(Material.AIR);
                                        }
                                        e.getPlayer().getInventory().addItem(new ItemStack(Material.SUGAR_CANE, blocks.size()));
                                    }
                                }
                            }
                        }, 200L);
                    }

                }
            }
        }
    }

    //Imported Paper to use this event
    @EventHandler
    public void onMove(EntityMoveEvent e){
        if(!(e.getEntity() instanceof MagmaCube)) return;
        if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            MagmaCube cube = (MagmaCube) e.getEntity();
            if(e.getTo().getBlock().getType().equals(Material.AIR)) return;
            UUID uuid = UUID.fromString(cube.getMetadata("id").get(0).asString());
            List<Location> location1= this.locations.containsKey(uuid) ? this.locations.get(uuid) : new ArrayList<>();
            location1.add(e.getTo());
            if(this.locations.containsKey(uuid)) this.locations.remove(uuid);
            this.locations.put(uuid, location1);
        }
    }

    public boolean isBottom(Block b) {
        Block bBelow = b.getRelative(BlockFace.DOWN);
        if(b.getType().equals(bBelow.getType())) {
            return false;
        }

        return true;
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
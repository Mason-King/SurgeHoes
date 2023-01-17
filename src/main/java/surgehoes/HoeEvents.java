package surgehoes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

public class HoeEvents implements Listener {

    private Plugin plugin;

    //easier definition of name space keys for optimization!
    private NamespacedKey harvester;
    private NamespacedKey autosell;
    private NamespacedKey levelKey;
    private NamespacedKey xpKey;

    public HoeEvents(Plugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.harvester = new NamespacedKey(plugin, "harvester");
        this.autosell = new NamespacedKey(plugin, "autosellEnabled");
        this.levelKey = new NamespacedKey(plugin, "level");
        this.xpKey = new NamespacedKey(plugin, "xp");

    }

    @EventHandler
    public void caneBreak(PlayerInteractEvent e) {
        if(!e.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
        ItemStack hoe = e.getItem();
        ItemMeta im = hoe.getItemMeta();
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        if(!pdc.has(harvester, PersistentDataType.STRING)) return;
        e.setCancelled(true);
        if(isBottom(e.getClickedBlock())) return;
        if(pdc.get(autosell, PersistentDataType.STRING) == "true") {
            //They have autosell!
        } else {
            Block block = e.getClickedBlock();
            int cane = getBlocks(block).size();
            for(Block b : getBlocks(block)) {
                b.setType(Material.AIR);
            }
            e.getPlayer().getInventory().addItem(new ItemStack(Material.SUGAR_CANE, cane));
        }

        //xp and leveling goes here!
        int level = pdc.getOrDefault(levelKey, PersistentDataType.INTEGER, 1);
        int xp = pdc.getOrDefault(xpKey, PersistentDataType.INTEGER, 0);
        int xpNeeded = (int) (Math.pow(level, 2) * 100);

        //Might have to play with this number!
        xp += 5;
        if(xp > xpNeeded) {
            //level up!
            level++;
            xp = 0;
            xpNeeded = (int) (Math.pow(level, 2) * 100);
            im.setDisplayName(Utils.color("&f&lHarvester Hoe &7| ("+level+")"));
        }

        pdc.set(levelKey, PersistentDataType.INTEGER, level);
        pdc.set(xpKey, PersistentDataType.INTEGER, xp);

        List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();

        if(lore.get(lore.size() -1).startsWith("XP")) {
            lore.remove(lore.size() -1);
        }

        im.setLore(generateLore(hoe, xpNeeded));
        hoe.setItemMeta(im);

    }

    @EventHandler
    public void guiOpen(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            HoeGui gui = new HoeGui(plugin);
            gui.getMainGui().show(e.getPlayer(), 0);
        } else return;
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

    public boolean isBottom(Block b) {
        Block bBelow = b.getRelative(BlockFace.DOWN);
        if(b.getType().equals(bBelow.getType())) {
            return false;
        }

        return true;
    }


    public List<String> generateLore(ItemStack stack, int xpNeeded) {
        List<String> lore = new ArrayList<>();
        lore.add(Utils.color("&7Harvest crops to gain stars to upgrade"));
        lore.add(Utils.color("&7your tools"));
        lore.add(Utils.color("&7"));

        PersistentDataContainer dc = stack.getItemMeta().getPersistentDataContainer();
        double percentage =  ((double) (dc.get(xpKey, PersistentDataType.INTEGER)) / xpNeeded * 100);

        lore.add(Utils.color("&f&lProgress &7| "+Math.floor(percentage)+"%"));

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

        lore.add(Utils.color("&a"+greenBar+"&c"+redBar+""));
        lore.add(Utils.color("&7"));
        lore.add(Utils.color("&7&o(( Right-click to upgrade ))"));

        return lore;
    }

}

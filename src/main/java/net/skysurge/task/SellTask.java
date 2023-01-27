package net.skysurge.task;

import net.minecraft.network.chat.IChatBaseComponent;
import net.skysurge.Main;
import net.skysurge.Utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SellTask extends BukkitRunnable {

    private Main main;

    public Map<UUID, Integer> toSell;

    public SellTask(Main main) {
        this.main = main;
        this.toSell = new HashMap<>();
    }

    @Override
    public void run() {
        for(Map.Entry e : this.toSell.entrySet()) {
            Player p = Bukkit.getPlayer((UUID) e.getKey());
            int cane = (int) e.getValue();
            //could be changed later!
            int price = 10;
            int total = cane * price;

            p.sendMessage(ChatUtils.color("&f&lSkySurge &7| You have sold &b&lx" + cane + " &7cane for &b&l$" + total));
            toSell.remove(p.getUniqueId());
            this.main.getEcon().depositPlayer(p, total);
        }
    }

}

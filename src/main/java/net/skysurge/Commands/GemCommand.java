package net.skysurge.Commands;

import net.skysurge.Main;
import net.skysurge.Utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GemCommand implements CommandExecutor {

    Main main;

    public GemCommand(Main main) {
        this.main = main;

        main.getCommand("gems").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(args.length == 0) {
            p.sendMessage(ChatUtils.color("&f&lSkySurge &7| You currently have " + main.getGemUtils().getGems(p) + " gems."));
        } else if(args[0].equalsIgnoreCase("give")) {
            if(args.length < 3) {
                p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Invalid usage, try: /gems give <player> <amount>"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null || !target.isOnline()) {
                p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Sorry! We could not find this player"));
                return false;
            }
            if(!args[2].matches("-?\\d+")) {
                p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Sorry! Please provide a valid number"));
                return false;
            }
            int amount = Integer.parseInt(args[2]);

            main.getGemUtils().addGems(target, amount);
            p.sendMessage(ChatUtils.color("&f&lSkySurge &7| You have given " + target.getName() + " " + amount + " gems!"));
        } else if(args[0].equalsIgnoreCase("set")) {
            if(args.length < 3) {
                p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Invalid usage, try: /gems set <player> <amount>"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null || !target.isOnline()) {
                p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Sorry! We could not find this player"));
                return false;
            }
            if(!args[2].matches("-?\\d+")) {
                p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Sorry! Please provide a valid number"));
                return false;
            }
            int amount = Integer.parseInt(args[2]);

            main.getGemUtils().setGems(target, amount);
            p.sendMessage(ChatUtils.color("&f&lSkySurge &7| You have set " + target.getName() + "'s gems to " + amount));
        } else if(args[0].equalsIgnoreCase("remove")) {
            if(args.length < 3) {
                p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Invalid usage, try: /gems remove <player> <amount>"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null || !target.isOnline()) {
                p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Sorry! We could not find this player"));
                return false;
            }
            if(!args[2].matches("-?\\d+")) {
                p.sendMessage(ChatUtils.color("&f&lSkySurge &7| Sorry! Please provide a valid number"));
                return false;
            }
            int amount = Integer.parseInt(args[2]);

            main.getGemUtils().removeGems(target, amount);
            p.sendMessage(ChatUtils.color("&f&lSkySurge &7| You have removed " + amount + " gems from " + target.getName()));
        }
        return false;
    }
}

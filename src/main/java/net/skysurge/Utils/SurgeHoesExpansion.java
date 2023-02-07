package net.skysurge.Utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.skysurge.Main;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class SurgeHoesExpansion extends PlaceholderExpansion {

    private Main main;

    public SurgeHoesExpansion(Main main) {
        this.main = main;
        this.register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "SurgeHoes";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Mason";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("gems")) {
            return main.getGemUtils().getGems(player.getUniqueId()) + "";
        }
        return null;
    }
}

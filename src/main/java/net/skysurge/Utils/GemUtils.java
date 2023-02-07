package net.skysurge.Utils;

import net.skysurge.Main;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GemUtils {

    private Main main;

    public GemUtils(Main main) {
        this.main = main;
    }

    public int getGems(Player p) {
        if(this.main.getDb().exists("playerData", "uuid", p.getUniqueId().toString())) {
            return main.getDb().getInt("playerData", "uuid", p.getUniqueId().toString(), "gems");
        }
        return 0;
    }

    public int getGems(UUID uuid) {
        if(this.main.getDb().exists("playerData", "uuid", uuid.toString())) {
            return main.getDb().getInt("playerData", "uuid", uuid.toString(), "gems");
        }
        return 0;
    }

    public void addGems(Player p, int amount) {
        if(this.main.getDb().exists("playerData", "uuid", p.getUniqueId().toString())) {
            this.main.getDb().execute("REPLACE INTO playerData (uuid, gems) VALUES ('"+p.getUniqueId()+"', '"+ (getGems(p) + amount) +"')");
        }
    }

    public void setGems(Player p, int amount) {
        if(this.main.getDb().exists("playerData", "uuid", p.getUniqueId().toString())) {
            this.main.getDb().execute("REPLACE INTO playerData (uuid, gems) VALUES ('"+p.getUniqueId()+"', '"+ amount +"')");
        }
    }

    public void removeGems(Player p, int amount) {
        if(this.main.getDb().exists("playerData", "uuid", p.getUniqueId().toString())) {
            this.main.getDb().execute("REPLACE INTO playerData (uuid, gems) VALUES ('"+p.getUniqueId()+"', '"+ (getGems(p) - amount) +"')");
        }
    }
}

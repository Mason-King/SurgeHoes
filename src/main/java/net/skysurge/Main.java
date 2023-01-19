package net.skysurge;

import net.skysurge.Commands.HoeCommand;
import net.skysurge.Events.BlockBreak;
import net.skysurge.Gui.HoeGui;
import net.skysurge.Storage.Database;
import net.skysurge.Storage.SQLite;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    //definition of all the namespace keys needed!
    //Used for basic data storage
    private NamespacedKey harvesterKey = new NamespacedKey(this, "Harvester");
    private NamespacedKey lifetimeCaneKey = new NamespacedKey(this, "LifetimeCane");
    private NamespacedKey lifetimeGemsKey = new NamespacedKey(this, "LifetimeGems");

    //Used to handle the hoe's current level and XP
    private NamespacedKey levelKey = new NamespacedKey(this, "level");
    private NamespacedKey xpKey = new NamespacedKey(this, "xp");

    //Used to hold the enchant's current levels of each enchant
    private NamespacedKey laserKey = new NamespacedKey(this, "laser");
    private NamespacedKey nightFallKey = new NamespacedKey(this, "nightFall");
    private NamespacedKey magmaStompKey = new NamespacedKey(this, "magmaStomp");
    private NamespacedKey blackHoleKey = new NamespacedKey(this, "blackHole");
    private NamespacedKey arrowRainKey = new NamespacedKey(this, "arrowRain");
    private NamespacedKey moneyPouchKey = new NamespacedKey(this, "moneyPouch");
    private NamespacedKey xpPouchKey = new NamespacedKey(this, "xpPouch");
    private NamespacedKey gemPouchKey = new NamespacedKey(this, "gemPouch");
    private NamespacedKey keyFinderKey = new NamespacedKey(this, "keyFinder");
    private NamespacedKey spawnerFinderKey = new NamespacedKey(this, "spawnerFinder");
    private NamespacedKey autoSellKey = new NamespacedKey(this, "autosell");

    //used to detect weather or not the enchant is enabled or not!
    private NamespacedKey laserEnabledKey = new NamespacedKey(this, "laserEnabled");
    private NamespacedKey nightFallEnabledKey = new NamespacedKey(this, "nightFallEnabled");
    private NamespacedKey magmaStompEnabledKey = new NamespacedKey(this, "magmaStompEnabled");
    private NamespacedKey blackHoleEnabledKey = new NamespacedKey(this, "blackHoleEnabled");
    private NamespacedKey arrowRainEnabledKey = new NamespacedKey(this, "arrowRainEnabled");
    private NamespacedKey moneyPouchEnabledKey = new NamespacedKey(this, "moneyPouchEnabled");
    private NamespacedKey xpPouchEnabledKey = new NamespacedKey(this, "xpPouchEnabled");
    private NamespacedKey gemPouchEnabledKey = new NamespacedKey(this, "gemPouchEnabled");
    private NamespacedKey keyFinderEnabledKey = new NamespacedKey(this, "keyFinderEnabled");
    private NamespacedKey spawnerFinderEnabledKey = new NamespacedKey(this, "spawnerFinderEnabled");
    private NamespacedKey autoSellEnabledKey = new NamespacedKey(this, "autosellEnabled");

    private HoeGui hoeGui;
    private Database db;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.db = new SQLite(this);
        this.db.load();
        new HoeCommand(this);
        new BlockBreak(this);
        this.hoeGui = new HoeGui(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public NamespacedKey getHarvesterKey() {
        return harvesterKey;
    }

    public NamespacedKey getLifetimeCaneKey() {
        return lifetimeCaneKey;
    }

    public NamespacedKey getLifetimeGemsKey() {
        return lifetimeGemsKey;
    }

    public NamespacedKey getLevelKey() {
        return levelKey;
    }

    public NamespacedKey getXpKey() {
        return xpKey;
    }

    public NamespacedKey getLaserKey() {
        return laserKey;
    }

    public NamespacedKey getNightFallKey() {
        return nightFallKey;
    }

    public NamespacedKey getMagmaStompKey() {
        return magmaStompKey;
    }

    public NamespacedKey getBlackHoleKey() {
        return blackHoleKey;
    }

    public NamespacedKey getArrowRainKey() {
        return arrowRainKey;
    }

    public NamespacedKey getMoneyPouchKey() {
        return moneyPouchKey;
    }

    public NamespacedKey getXpPouchKey() {
        return xpPouchKey;
    }

    public NamespacedKey getGemPouchKey() {
        return gemPouchKey;
    }

    public NamespacedKey getKeyFinderKey() {
        return keyFinderKey;
    }

    public NamespacedKey getSpawnerFinderKey() {
        return spawnerFinderKey;
    }

    public NamespacedKey getAutoSellKey() {
        return autoSellKey;
    }

    public NamespacedKey getLaserEnabledKey() {
        return laserEnabledKey;
    }

    public NamespacedKey getNightFallEnabledKey() {
        return nightFallEnabledKey;
    }

    public NamespacedKey getMagmaStompEnabledKey() {
        return magmaStompEnabledKey;
    }

    public NamespacedKey getBlackHoleEnabledKey() {
        return blackHoleEnabledKey;
    }

    public NamespacedKey getArrowRainEnabledKey() {
        return arrowRainEnabledKey;
    }

    public NamespacedKey getMoneyPouchEnabledKey() {
        return moneyPouchEnabledKey;
    }

    public NamespacedKey getXpPouchEnabledKey() {
        return xpPouchEnabledKey;
    }

    public NamespacedKey getGemPouchEnabledKey() {
        return gemPouchEnabledKey;
    }

    public NamespacedKey getKeyFinderEnabledKey() {
        return keyFinderEnabledKey;
    }

    public NamespacedKey getSpawnerFinderEnabledKey() {
        return spawnerFinderEnabledKey;
    }

    public NamespacedKey getAutoSellEnabledKey() {
        return autoSellEnabledKey;
    }

    public HoeGui getHoeGui() {
        return hoeGui;
    }

    public Database getDb() {
        return db;
    }
}

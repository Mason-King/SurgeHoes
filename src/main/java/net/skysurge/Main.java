package net.skysurge;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.skysurge.Commands.GemCommand;
import net.skysurge.Commands.HoeCommand;
import net.skysurge.Events.*;
import net.skysurge.Gui.CaptchaGui;
import net.skysurge.Gui.HoeGui;
import net.skysurge.Storage.Database;
import net.skysurge.Storage.SQLite;
import net.skysurge.Utils.GemUtils;
import net.skysurge.task.SellTask;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.RegisteredServiceProvider;
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
    private GemUtils gemUtils;
    private Database db;

    private Economy econ = null;
    private Permission perms = null;
    private Chat chat = null;

    private SellTask task;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        this.db = new SQLite(this);
        this.db.load();

        this.task = new SellTask(this);

        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

        new ArrowRain(this);
        new MagmaStomp(this);
        new XpPouch(this);
        new MoneyPouch(this);
        new GemPouch(this);
        new Laser(this);
        new NightFall(this);
        new HoeCommand(this);
        new BlockBreak(this);
        this.hoeGui = new HoeGui(this);
        new GemCommand(this);
        this.gemUtils = new GemUtils(this);

        this.task.runTaskTimer(this, 0, 20 * 60 * 2);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
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

    public GemUtils getGemUtils() {
        return gemUtils;
    }

    public Economy getEcon() {
        return econ;
    }

    public Permission getPerms() {
        return perms;
    }

    public Chat getChat() {
        return chat;
    }

    public SellTask getTask() {
        return task;
    }

}

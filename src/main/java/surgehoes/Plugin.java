package surgehoes;

import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new HoeCommand(this);
        new HoeEvents(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

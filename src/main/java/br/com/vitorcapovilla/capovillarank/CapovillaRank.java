package br.com.vitorcapovilla.capovillarank;

import br.com.vitorcapovilla.capovillarank.manager.NametagManager;
import br.com.vitorcapovilla.capovillarank.manager.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CapovillaRank extends JavaPlugin {

    private RankManager rankManager;
    private NametagManager nametagManager;

    @Override
    public void onEnable() {
        getCommand("rank").setExecutor(new RankCommand(this));

        rankManager = new RankManager(this);
        nametagManager = new NametagManager(this);

        Bukkit.getPluginManager().registerEvents(new RankListener(this), this);
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public NametagManager getNametagManager() {
        return nametagManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

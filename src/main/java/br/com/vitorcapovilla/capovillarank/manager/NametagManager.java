package br.com.vitorcapovilla.capovillarank.manager;

import br.com.vitorcapovilla.capovillarank.CapovillaRank;
import br.com.vitorcapovilla.capovillarank.Rank;
import jdk.tools.jmod.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class NametagManager {

    private CapovillaRank main;

    public NametagManager(CapovillaRank main){
        this.main = main;
    }

    public void setNametags(Player p){

        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        for (Rank rank : Rank.values()){
            Team team = p.getScoreboard().registerNewTeam(rank.name());
            team.setPrefix(rank.getDisplay());
        }

        for (Player target : Bukkit.getOnlinePlayers()){
            if (p.getUniqueId() != target.getUniqueId()){
                p.getScoreboard().getTeam(main.getRankManager().getRank(target.getUniqueId()).name()).addEntry(target.getName());
            }

        }

    }

    public void newTag(Player p){
        Rank rank = main.getRankManager().getRank(p.getUniqueId());
        for (Player target : Bukkit.getOnlinePlayers()){
            target.getScoreboard().getTeam(rank.name()).addEntry(p.getName());
        }
    }

    public void removeTag(Player p){
        for (Player target : Bukkit.getOnlinePlayers()){
            target.getScoreboard().getEntryTeam(p.getName()).removeEntry(p.getName());
        }
    }

}

package br.com.vitorcapovilla.capovillarank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankCommand implements CommandExecutor, TabCompleter {

    private CapovillaRank main;

    public RankCommand(CapovillaRank main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (player.hasPermission("capovillarank.rank") || player.isOp()){
                if (args.length == 2){
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (target.hasPlayedBefore() || target.isOnline()) {
                        Rank newRank = getRankByName(args[1]);
                        if (newRank != null) {
                            main.getRankManager().setRank(target.getUniqueId(), newRank, false);

                            player.sendMessage(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "[CapovillaRank] " + ChatColor.RESET.toString() + ChatColor.GREEN + "Você mudou o rank de " + target.getName() + " para " + newRank.getDisplay());

                            if (target.isOnline()) {
                                target.getPlayer().sendMessage(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "[CapovillaRank] " + ChatColor.RESET.toString() + ChatColor.GREEN + "Seu rank foi alterado " + newRank.getDisplay());
                            }
                        } else {
                            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[CapovillaRank] " + ChatColor.RESET.toString() + ChatColor.RED + "Rank inválido!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[CapovillaRank] " + ChatColor.RESET.toString() + ChatColor.RED + "Esse jogador nunca entrou no servidor!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[CapovillaRank] " + ChatColor.RESET.toString() + ChatColor.RED + "Use /rank <player> <rank>");
                }
            } else {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[CapovillaRank] " + ChatColor.RESET.toString() + ChatColor.RED + "Você não tem permissão para utilizar esse comando!");
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames;
        } else if (args.length == 2) {
            List<String> rankNames = new ArrayList<>();
            for (Rank rank : Rank.values()) {
                rankNames.add(rank.name());
            }
            return rankNames;
        }
        return Arrays.asList();
    }

    private Rank getRankByName(String name) {
        for (Rank rank : Rank.values()) {
            if (rank.name().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }
}

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

            if (player.isOp()){
                if (args.length == 2){
                    if (Bukkit.getOfflinePlayer(args[0]) != null){
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                        for (Rank rank : Rank.values()){
                            if (rank.name().equalsIgnoreCase(args[1])){
                                main.getRankManager().setRank(target.getUniqueId(), rank, false);

                                player.sendMessage(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "[CapovillaRank] "+ ChatColor.RESET.toString() + ChatColor.GREEN + "Você mudou o rank de "+ target.getName() + " para " + rank.getDisplay());

                                if (target.isOnline()){
                                    target.getPlayer().sendMessage(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "[CapovillaRank] "+ ChatColor.RESET.toString() + ChatColor.GREEN + "Seu rank foi alterado "+ rank.getDisplay());
                                }

                                return false;
                            }
                        }

                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[CapovillaRank] "+ ChatColor.RESET.toString() + ChatColor.RED + "Você não especificou um rank!");
                    }else{
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[CapovillaRank] "+ ChatColor.RESET.toString() + ChatColor.RED + "Esse jogador nunca entrou no servidor!");
                    }
                }else{
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[CapovillaRank] "+ ChatColor.RESET.toString() + ChatColor.RED + "Use /rank <player> <rank>");
                }
            }else{
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[CapovillaRank] "+ ChatColor.RESET.toString() + ChatColor.RED + "Você não tem permissão para utilizar esse comando!");
            }

        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) { // Se o jogador digitou apenas /rank
            String partialPlayer = args[0].toLowerCase(); // Argumento parcial fornecido pelo jogador
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(partialPlayer)) {
                    completions.add(player.getName());
                }
            }
        } else if (args.length == 2) {
            String partialRank = args[1].toLowerCase();
            for (Rank rank : Rank.values()) {
                if (rank.name().toLowerCase().startsWith(partialRank)) {
                    completions.add(rank.name());
                }
            }
        }

        return completions;
    }
}

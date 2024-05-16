package br.com.vitorcapovilla.capovillarank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {

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
}

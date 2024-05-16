package br.com.vitorcapovilla.capovillarank;

import org.bukkit.ChatColor;

public enum Rank {

    Dono(ChatColor.DARK_RED.toString() + ChatColor.BOLD +"[Dono] ", new String[]{}),
    SubDono(ChatColor.RED.toString() + ChatColor.BOLD + "[SubDono] ", new String[]{}),
    Admin(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "[Admin] ", new String[]{}),
    Mod(ChatColor.GREEN.toString().toString() + ChatColor.BOLD + "[Mod] ", new String[]{}),
    Trial(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "[Trial] ", new String[]{}),
    Ajudante(ChatColor.YELLOW.toString() + ChatColor.BOLD + "[Ajudante] ", new String[]{}),
    YouTuber(ChatColor.AQUA.toString() + ChatColor.BOLD + "[YT] ", new String[]{}),
    MVP_Plus(ChatColor.GOLD + "[MVP+] ", new String[]{}),
    MVP(ChatColor.DARK_AQUA + "[MVP] ", new String[]{}),
    VIP(ChatColor.GREEN + "[VIP] ", new String[]{}),
    Membro(ChatColor.YELLOW + "[Membro] ", new String[]{});

    private String display;
    private String[] permissions;

    Rank(String display, String[] permissions){
        this.display = display;
        this.permissions = permissions;
    }

    public String getDisplay(){
        return display;
    }

    public String[] getPermissions(){
        return permissions;
    }

}

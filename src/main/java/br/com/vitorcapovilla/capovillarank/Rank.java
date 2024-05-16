package br.com.vitorcapovilla.capovillarank;

import org.bukkit.ChatColor;

public enum Rank {

    Dono(ChatColor.DARK_RED.toString() + ChatColor.BOLD +"[Dono] ", new String[]{}, 10),
    SubDono(ChatColor.RED.toString() + ChatColor.BOLD + "[SubDono] ", new String[]{}, 9),
    Admin(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "[Admin] ", new String[]{}, 8),
    Mod(ChatColor.GREEN.toString().toString() + ChatColor.BOLD + "[Mod] ", new String[]{}, 7),
    Trial(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "[Trial] ", new String[]{}, 6),
    Ajudante(ChatColor.YELLOW.toString() + ChatColor.BOLD + "[Ajudante] ", new String[]{}, 5),
    YouTuber(ChatColor.AQUA.toString() + ChatColor.BOLD + "[YT] ", new String[]{}, 4),
    MVP_Plus(ChatColor.GOLD + "[MVP+] ", new String[]{}, 3),
    MVP(ChatColor.DARK_AQUA + "[MVP] ", new String[]{}, 2),
    VIP(ChatColor.GREEN + "[VIP] ", new String[]{}, 1),
    Membro(ChatColor.YELLOW + "[Membro] ", new String[]{}, 0);

    private String display;
    private String[] permissions;
    private int nvl;

    Rank(String display, String[] permissions, int nvl){
        this.display = display;
        this.permissions = permissions;
        this.nvl = nvl;
    }

    public String getDisplay(){
        return display;
    }

    public String[] getPermissions(){
        return permissions;
    }

    public int getNvl(){
        return nvl;
    }

}

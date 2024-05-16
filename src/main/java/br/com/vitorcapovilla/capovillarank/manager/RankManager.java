package br.com.vitorcapovilla.capovillarank.manager;

import br.com.vitorcapovilla.capovillarank.CapovillaRank;
import br.com.vitorcapovilla.capovillarank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class RankManager {

    private final CapovillaRank main;

    private final File file;
    private final YamlConfiguration config;

    private final HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    public RankManager(CapovillaRank main){
        this.main = main;

        if (!main.getDataFolder().exists()){
            main.getDataFolder().mkdir();
        }

        file = new File(main.getDataFolder(), "ranks.yml");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

    }


    public void setRank(UUID uuid, Rank rank, boolean firstJoin){

        if (Bukkit.getOfflinePlayer(uuid).isOnline() && !firstJoin){
            Player p = Bukkit.getPlayer(uuid);
            PermissionAttachment attachment;
            if (perms.containsKey(uuid)){
                attachment = perms.get(uuid);
            }else{
                attachment = p.addAttachment(main);
                perms.put(uuid, attachment);
            }

            for (String perm : getRank(uuid).getPermissions()){
                if (p.hasPermission(perm)){
                    attachment.unsetPermission(perm);
                }
            }

            for (String perm : rank.getPermissions()){
                attachment.setPermission(perm, true);
            }
        }

        config.set(uuid.toString(), rank.name());

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Bukkit.getOfflinePlayer(uuid).isOnline()){
            Player player = Bukkit.getPlayer(uuid);
            main.getNametagManager().removeTag(player);
            main.getNametagManager().newTag(player);
        }

    }

    public Rank getRank(UUID uuid){
        return Rank.valueOf(config.getString(uuid.toString()));
    }
    public HashMap<UUID, PermissionAttachment> getPerms() {
        return perms;
    }

}

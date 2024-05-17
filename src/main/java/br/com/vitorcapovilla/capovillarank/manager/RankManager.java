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
import java.util.List;
import java.util.UUID;

public class RankManager {

    private final CapovillaRank main;
    private final File file;
    private final YamlConfiguration config;
    private final HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    public RankManager(CapovillaRank main) {
        this.main = main;

        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }

        file = new File(main.getDataFolder(), "ranks.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
        initializeRanks(); // Ensure ranks structure is initialized after loading config
    }

    private void initializeRanks() {
        boolean saveNeeded = false;

        if (!config.contains("ranks")) {
            config.createSection("ranks");
            saveNeeded = true;
        }

        for (Rank rank : Rank.values()) {
            String rankPath = "ranks." + rank.name();
            if (!config.contains(rankPath)) {
                config.createSection(rankPath);
                config.set(rankPath + ".permissions", List.of(rank.getPermissions()));
                saveNeeded = true;
            } else {
                // Load existing permissions from config to Rank enum
                List<String> permissions = config.getStringList(rankPath + ".permissions");
                rank.setPermissions(permissions.toArray(new String[0]));
            }
        }

        if (saveNeeded) {
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setRank(UUID uuid, Rank rank, boolean firstJoin) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline() && !firstJoin) {
            Player p = Bukkit.getPlayer(uuid);
            PermissionAttachment attachment;
            if (perms.containsKey(uuid)) {
                attachment = perms.get(uuid);
            } else {
                attachment = p.addAttachment(main);
                perms.put(uuid, attachment);
            }

            // Remove old permissions
            Rank oldRank = getRank(uuid);
            List<String> oldPermissions = getRankPermissions(oldRank);
            if (oldPermissions != null) {
                for (String perm : oldPermissions) {
                    if (p.hasPermission(perm)) {
                        attachment.unsetPermission(perm);
                    }
                }
            }

            // Add new permissions
            List<String> newPermissions = getRankPermissions(rank);
            if (newPermissions != null) {
                for (String perm : newPermissions) {
                    attachment.setPermission(perm, true);
                }
            }
        }

        config.set("players." + uuid.toString(), rank.name());
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
            Player player = Bukkit.getPlayer(uuid);
            main.getNametagManager().removeTag(player);
            main.getNametagManager().newTag(player);
        }
    }

    public Rank getRank(UUID uuid) {
        String rankName = config.getString("players." + uuid.toString());
        if (rankName == null || rankName.isEmpty()) {
            return Rank.Membro; // Use the default rank as Membro
        }
        try {
            return Rank.valueOf(rankName);
        } catch (IllegalArgumentException e) {
            // Handle case where rankName does not correspond to any valid Rank
            return Rank.Membro; // Use the default rank as Membro
        }
    }

    public List<String> getRankPermissions(Rank rank) {
        return config.getStringList("ranks." + rank.name() + ".permissions");
    }

    public HashMap<UUID, PermissionAttachment> getPerms() {
        return perms;
    }
}

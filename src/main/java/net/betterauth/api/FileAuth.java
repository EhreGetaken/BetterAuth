package net.betterauth.api;

import net.betterauth.BetterAuth;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class FileAuth {

    public static File playerdata;
    public static FileConfiguration cfg;

    public static void readConfig() {
        playerdata = new File(BetterAuth.getInstance().getDataFolder(), "fileauth.yml");
        cfg = YamlConfiguration.loadConfiguration(playerdata);
    }

    public static boolean playerExists(UUID uuid) {
        if (cfg.contains("User." + uuid)) {
            return true;
        }
        return false;
    }

    public static void createPlayer(UUID uuid) {
        if (!playerExists(uuid)) {
            cfg.set("User." + uuid + ".auth", false);
            saveFile();
        }
    }

    public static boolean isUserAuthenticated(UUID uuid) {
        if (playerExists(uuid)) {
            if (cfg.getBoolean("User." + uuid + ".auth")) {
                return true;
            }
        } else {
            createPlayer(uuid);
            return isUserAuthenticated(uuid);
        }
        return false;
    }

    public static void setUserAuthenticated(UUID uuid, boolean authenticated) {
        if (playerExists(uuid)) {
            cfg.set("User." + uuid + ".auth", authenticated);
            saveFile();
        } else {
            createPlayer(uuid);
            setUserAuthenticated(uuid, authenticated);
        }
    }

    public static void saveFile() {
        try {
            cfg.save(playerdata);
        } catch (Exception exc) {
            Bukkit.getConsoleSender().sendMessage("[BetterAuth] Something went wrong while saving file 'fileauth.yml' !");
            exc.printStackTrace();
        }
    }

}

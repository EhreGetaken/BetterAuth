package net.betterauth;

import net.betterauth.api.FileAuth;
import net.betterauth.listener.ChatListener;
import net.betterauth.listener.JoinListener;
import net.betterauth.listener.MoveListener;
import net.betterauth.listener.QuitListener;
import net.betterauth.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BetterAuth extends JavaPlugin {

    public static BetterAuth instance;

    public static ExecutorService executorService = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("[BetterAuth] Registering config...");
        registerConfig();

        if (getConfig().getString("Settings.SaveType").equalsIgnoreCase("FILE")) {
            FileAuth.readConfig();
            System.out.println("[BetterAuth] Hooked into FileAuthAPI!");
        } else {
            MySQL.setStandardMySQL();
            MySQL.readMySQL();
            MySQL mySQL = new MySQL();
            MySQL.connect();
            MySQL.createTable();
            System.out.println("[BetterAuth] Hooked into SqlAuthAPI!");
        }

        registerListener();

    }

    @Override
    public void onDisable() {
        executorService.shutdown();
    }

    public static BetterAuth getInstance() {
        return instance;
    }

    private void registerConfig() {
        Configuration cfg = this.getConfig();

        cfg.options().copyDefaults(true);
        this.getConfig().options().header("System by PokeArtZ | Copyright (c) 2019 Espen da Silva. All rights reserved!");
        cfg.addDefault("Settings.Prefix", "&6&lBetterAuth &8> ");
        //ADD KICK TIME IN TICKS 4 EACH PLAYER (ASYNC)
        cfg.addDefault("Settings.KickTimeInTicks", "1000");
        cfg.addDefault("Settings.UserID", "YOURUSERIDHERE");
        cfg.addDefault("Settings.SaveType", "FILE");
        cfg.addDefault("Messages.NotAuth", "&7Please follow the instructions in the &6&lchat&7!");
        cfg.addDefault("Messages.WordAuth", "&6Please type in this &6&lword&8: &6&l%w");
        cfg.addDefault("Messages.MathAuth", "&7What is &6&l%f &8+ &6&l%f2 &7?");
        cfg.addDefault("Messages.NotAuthJoin", "&7Please follow the &6&linstructions &7in the &6&lchat&7!");
        cfg.addDefault("Messages.AuthComplete", "&7You are &6&lauthenticated&7!");
        cfg.addDefault("Messages.TrieRemove", "&7You have &6&l%t &7tires left!");
        cfg.addDefault("Messages.Kick", "&7You used up your attempts!");

        this.saveConfig();
    }

    private void registerListener() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinListener(), instance);
        pm.registerEvents(new ChatListener(), instance);
        pm.registerEvents(new MoveListener(), instance);
        pm.registerEvents(new QuitListener(), instance);
    }

}

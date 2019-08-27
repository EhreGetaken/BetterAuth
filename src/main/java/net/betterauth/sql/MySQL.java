package net.betterauth.sql;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    public static String host;
    public static String user;
    public static String password;
    public static String database;
    public static String port;
    public static Connection con;
    private static RequestQueue requestQ;

    public MySQL()
    {
        requestQ = new RequestQueue();
        requestQ.setRunning(true);
    }
    public static Connection getConnection() {
        return con;
    }
    public static void connect() {
        if (con == null)
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + password + "&autoReconnect=true");
            } catch (SQLException e) {
            }
    }
    public static void close()
    {
        if (con != null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    public static void update(String qry)
    {
        requestQ.addToQueue(qry);
    }

    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS BetterAuth (UUID VARCHAR(100), AUTH VARCHAR(100))");
    }

    public static java.io.File getMySQLFile() {
        return new java.io.File("plugins/BetterAuth", "MySQL.yml");
    }

    public static FileConfiguration getMySQLFileConfiguration() {
        return org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(getMySQLFile());
    }

    public static void setStandardMySQL() {
        FileConfiguration cfg = getMySQLFileConfiguration();

        cfg.options().copyDefaults(true);
        cfg.addDefault("username", "root");
        cfg.addDefault("password", "password");
        cfg.addDefault("database", "localhost");
        cfg.addDefault("host", "localhost");
        cfg.addDefault("port", "3306");
        try
        {
            cfg.save(getMySQLFile());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void readMySQL() {
        FileConfiguration cfg = getMySQLFileConfiguration();
        user = cfg.getString("username");
        password = cfg.getString("password");
        database = cfg.getString("database");
        host = cfg.getString("host");
        port = cfg.getString("port");
    }

}

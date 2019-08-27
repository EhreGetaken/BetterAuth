package net.betterauth.api;

import net.betterauth.sql.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SqlAuth {

    public static boolean playerExists(UUID uuid) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT UUID FROM BetterAuth WHERE UUID=?");
            ps.setString(1, uuid.toString());
            rs = ps.executeQuery();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        try {
            while (rs.next()) {
                if (rs != null) {
                    return true;
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return false;
    }

    public static void createPlayerObject(UUID uuid) {
        if (!playerExists(uuid)) {
            MySQL.update("INSERT INTO BetterAuth (UUID, AUTH) VALUES ('" + uuid + "', 'false')");
        }
    }

    public static boolean getAuthStatus(UUID uuid) {
        if (playerExists(uuid)) {
            ResultSet rs = null;
            try  {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT AUTH FROM BetterAuth WHERE UUID=?");
                ps.setString(1, uuid.toString());
                rs = ps.executeQuery();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }

            boolean isAuth = false;

            try {
                while (rs.next()) {
                    if (rs != null) {
                        isAuth = rs.getBoolean("AUTH");
                    }
                }
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            return isAuth;
        } else {
            createPlayerObject(uuid);
            getAuthStatus(uuid);
        }
        return false;
    }

    public static void setAuthStatus(UUID uuid, boolean auth) {
        if (playerExists(uuid)) {
            MySQL.update("UPDATE BetterAuth SET AUTH='" + auth + "' WHERE UUID='" + uuid + "'");
        } else {
            createPlayerObject(uuid);
            setAuthStatus(uuid, auth);
        }
    }

    public static boolean isUserAuthenticated(UUID uuid) {
        if (playerExists(uuid)) {
            ResultSet rs = null;
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT AUTH FROM BetterAuth WHERE UUID=?");
                ps.setString(1, uuid.toString());
                rs = ps.executeQuery();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            try {
                while (rs.next()) {
                    if (rs != null) {
                        return true;
                    }
                }
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            return false;
        } else {
            createPlayerObject(uuid);
            isUserAuthenticated(uuid);
        }
        return false;
    }

}

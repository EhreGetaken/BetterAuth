package net.betterauth.listener;

import net.betterauth.BetterAuth;
import net.betterauth.api.FileAuth;
import net.betterauth.api.SqlAuth;
import net.betterauth.api.Utils;
import net.betterauth.events.PlayerAuthEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (BetterAuth.getInstance().getConfig().getString("Settings.SaveType").equalsIgnoreCase("FILE")) {
            FileAuth.createPlayer(player.getUniqueId());
        } else {
            SqlAuth.createPlayerObject(player.getUniqueId());
        }

        if (BetterAuth.getInstance().getConfig().getString("Settings.SaveType").equalsIgnoreCase("FILE")) {
            if (!FileAuth.isUserAuthenticated(player.getUniqueId())) {
                int randomInt = Utils.getRandomInt(0, 1);
                if (randomInt == 0) {
                    Utils.sendWordAuth(player);
                } else {
                    Utils.sendMathAuth(player);
                }
            } else {
                if (!Utils.isPlayerAuth.contains(player)) {
                    Utils.isPlayerAuth.add(player);
                }
            }
        } else {

            if (Utils.cachedSqlAuth.contains(player)) {
                return;
            }

            if (!SqlAuth.isUserAuthenticated(player.getUniqueId())) {
                int randomInt = Utils.getRandomInt(0, 1);
                if (randomInt == 0) {
                    Utils.sendWordAuth(player);
                } else {
                    Utils.sendMathAuth(player);
                }
            } else {
                if (!Utils.cachedSqlAuth.contains(player)) {
                    Utils.cachedSqlAuth.add(player);
                    Utils.isPlayerAuth.add(player);
                }
            }
        }

    }

}

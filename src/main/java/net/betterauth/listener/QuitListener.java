package net.betterauth.listener;

import net.betterauth.api.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!Utils.isPlayerAuth.contains(player)) {
            Utils.wordAuth.remove(player);
            Utils.mathAuth.remove(player);
            Utils.tries.remove(player);
        }

    }

}

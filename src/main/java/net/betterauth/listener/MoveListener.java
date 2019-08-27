package net.betterauth.listener;

import net.betterauth.api.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class MoveListener implements Listener {

    private HashMap<String, Long> tcd = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!Utils.isPlayerAuth.contains(player)) {
            player.teleport(player.getLocation());

            Long time = Long.valueOf(System.currentTimeMillis());

            if (this.tcd.containsKey(player.getName())) {
                Long lastUse = this.tcd.get(player.getName());
                if (lastUse.longValue() + 2000L > time.longValue()) {
                    return;
                }
            }
            player.sendMessage(Utils.PREFIX + "§7Please follow the instructions in the §6§lchat§7!");

            this.tcd.put(player.getName(), time);

        }

    }

}

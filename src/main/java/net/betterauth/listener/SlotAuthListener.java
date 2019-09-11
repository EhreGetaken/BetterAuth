package net.betterauth.listener;

import net.betterauth.BetterAuth;
import net.betterauth.api.FileAuth;
import net.betterauth.api.SqlAuth;
import net.betterauth.api.Utils;
import net.betterauth.events.PlayerAuthEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class SlotAuthListener implements Listener {

    @EventHandler
    public void onHotbar(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        if (Utils.slotAuth.containsKey(player)) {
            int slot = event.getNewSlot();
            int hasToMove = Utils.slotAuth.get(player);
            if (slot == hasToMove) {
                Utils.mathAuth.remove(player);
                Utils.isPlayerAuth.add(player);
                Utils.cachedSqlAuth.add(player);
                Utils.wordAuth.remove(player);
                Utils.slotAuth.remove(player);
                player.sendMessage(Utils.PREFIX + Utils.getColoredMessage(Utils.authCompleteMessage));
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 2);
                Utils.tries.remove(player);
                Bukkit.getPluginManager().callEvent(new PlayerAuthEvent(player, "SlotAuth", String.valueOf(hasToMove)));
                if (BetterAuth.getInstance().getConfig().getString("Settings.SaveType").equalsIgnoreCase("FILE")) {
                    FileAuth.setUserAuthenticated(player.getUniqueId(), true);
                } else {
                    SqlAuth.setAuthStatus(player.getUniqueId(), true);
                }
            } else {
                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 0.5F, 0.5F);
            }
        }

    }

}

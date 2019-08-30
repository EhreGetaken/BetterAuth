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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!Utils.isPlayerAuth.contains(player)) {
            event.setCancelled(true);
            if (Utils.mathAuth.containsKey(player)) {
                Integer mathResult = Utils.mathAuth.get(player);
                if (message.equalsIgnoreCase(String.valueOf(mathResult))) {
                    Utils.mathAuth.remove(player);
                    Utils.isPlayerAuth.add(player);
                    Utils.cachedSqlAuth.add(player);
                    Utils.wordAuth.remove(player);
                    player.sendMessage(Utils.PREFIX + Utils.getColoredMessage(Utils.authCompleteMessage));
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 2);
                    Utils.tries.remove(player);
                    Bukkit.getPluginManager().callEvent(new PlayerAuthEvent(player, "MathAuth", String.valueOf(mathResult)));
                    if (BetterAuth.getInstance().getConfig().getString("Settings.SaveType").equalsIgnoreCase("FILE")) {
                        FileAuth.setUserAuthenticated(player.getUniqueId(), true);
                    } else {
                        SqlAuth.setAuthStatus(player.getUniqueId(), true);
                    }
                } else {
                    if (!Utils.tries.containsKey(player)) {
                        Utils.tries.put(player, 3);
                        player.sendMessage(Utils.PREFIX + Utils.getConvertedMessage(Utils.trieRemoveMessage, "%t", String.valueOf(Utils.tries.get(player))));
                    } else {
                        int trie = Utils.tries.get(player);
                        trie--;
                        player.sendMessage(Utils.PREFIX + Utils.getConvertedMessage(Utils.trieRemoveMessage, "%t", String.valueOf(trie)));
                        Utils.tries.remove(player);
                        Utils.tries.put(player, trie);
                        if (trie == 0) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(BetterAuth.getInstance(), () -> {
                                player.kickPlayer(Utils.PREFIX + Utils.getColoredMessage(Utils.kickMessage));
                            }, 2L);
                        }
                    }
                }
            }
            if (Utils.wordAuth.containsKey(player)) {
                String word = Utils.wordAuth.get(player);
                if (message.equalsIgnoreCase(word)) {
                    Utils.mathAuth.remove(player);
                    Utils.isPlayerAuth.add(player);
                    Utils.cachedSqlAuth.add(player);
                    Utils.wordAuth.remove(player);
                    player.sendMessage(Utils.PREFIX + Utils.getColoredMessage(Utils.authCompleteMessage));
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 2);
                    Utils.tries.remove(player);
                    Bukkit.getPluginManager().callEvent(new PlayerAuthEvent(player, "WordAuth", word));
                    if (BetterAuth.getInstance().getConfig().getString("Settings.SaveType").equalsIgnoreCase("FILE")) {
                        FileAuth.setUserAuthenticated(player.getUniqueId(), true);
                    } else {
                        SqlAuth.setAuthStatus(player.getUniqueId(), true);
                    }
                } else {
                    if (!Utils.tries.containsKey(player)) {
                        Utils.tries.put(player, 3);
                        player.sendMessage(Utils.PREFIX + Utils.getConvertedMessage(Utils.trieRemoveMessage, "%t", String.valueOf(Utils.tries.get(player))));
                    } else {
                        int trie = Utils.tries.get(player);
                        trie--;
                        player.sendMessage(Utils.PREFIX + Utils.getConvertedMessage(Utils.trieRemoveMessage, "%t", String.valueOf(trie)));
                        Utils.tries.remove(player);
                        Utils.tries.put(player, trie);
                        if (trie == 0) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(BetterAuth.getInstance(), () -> {
                                player.kickPlayer(Utils.PREFIX + Utils.getColoredMessage(Utils.kickMessage));
                            }, 2L);
                        }
                    }
                }
            }
        }

    }

}

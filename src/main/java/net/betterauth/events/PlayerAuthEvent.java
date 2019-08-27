package net.betterauth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAuthEvent extends Event implements Cancellable {

    public static HandlerList handlers = new HandlerList();
    public boolean cancelled = false;

    Player player;
    String authType;
    String authCode;

    public PlayerAuthEvent(Player player, String authType, String authCode) {
        this.player = player;
        this.authType = authType;
        this.authCode = authCode;
    }

    public Player getPlayer() {
        return player;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getAuthType() {
        return authType;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

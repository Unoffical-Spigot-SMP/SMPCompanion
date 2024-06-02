package net.overlordmc.smpcompanion.module.impl;

import net.overlordmc.smpcompanion.SMPCompanion;
import net.overlordmc.smpcompanion.module.Module;
import net.overlordmc.smpcompanion.module.ModuleProperties;
import net.overlordmc.smpcompanion.module.annotation.PropertyPath;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class LocalChatModule extends Module implements Listener {

    private final SMPCompanion plugin;
    private final ModuleProperties moduleProperties;

    @PropertyPath("distance")
    public double maxDistance = 150d;

    @PropertyPath("prefix")
    public String prefix = ".";

    public LocalChatModule(SMPCompanion plugin) {
        super("local-chat");
        this.plugin = plugin;
        this.moduleProperties = new ModuleProperties(plugin, this, this);
    }

    @Override
    public ModuleProperties getProperties() {
        return moduleProperties;
    }

    @Override
    public void onLoad() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (!message.startsWith(prefix)) return;

        event.setMessage(message.substring(prefix.length()));

        Player sender = event.getPlayer();
        World senderWorld = sender.getWorld();
        Location senderLocation = sender.getLocation();
        event.getRecipients().removeIf((recipient) -> {
            if (recipient.getWorld() != senderWorld) return true;

            double distance = senderLocation.distanceSquared(recipient.getLocation());
            System.out.println(distance);
            System.out.println(Math.pow(maxDistance, 2));
            return distance >= Math.pow(maxDistance, 2);
        });
    }

}

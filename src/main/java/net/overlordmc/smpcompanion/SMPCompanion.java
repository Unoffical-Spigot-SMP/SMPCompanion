package net.overlordmc.smpcompanion;

import net.overlordmc.smpcompanion.module.ModuleManager;
import net.overlordmc.smpcompanion.module.impl.LocalChatModule;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMPCompanion extends JavaPlugin {

    @Override
    public void onEnable() {
        ModuleManager moduleManager = new ModuleManager();
        moduleManager.register(new LocalChatModule(this));
        moduleManager.loadAll();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}

package net.overlordmc.smpcompanion.module;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final List<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();
    }

    public void register(Module module) {
        modules.add(module);
    }

    public void loadAll() {
        modules.forEach((module) -> {
            if (!module.getProperties().getDefaults().isEnabled) return;

            module.onLoad();
        });
    }

}

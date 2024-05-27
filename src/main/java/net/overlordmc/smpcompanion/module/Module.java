package net.overlordmc.smpcompanion.module;

import dev.dejvokep.boostedyaml.YamlDocument;

public abstract class Module {

    protected final String name;
    protected final YamlDocument configuration;

    protected Module(String name, YamlDocument configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    public String getName() {
        return name;
    }

}

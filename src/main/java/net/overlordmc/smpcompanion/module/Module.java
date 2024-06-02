package net.overlordmc.smpcompanion.module;

public abstract class Module {

    protected final String name;

    protected Module(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected abstract ModuleProperties getProperties();

    protected abstract void onLoad();

}

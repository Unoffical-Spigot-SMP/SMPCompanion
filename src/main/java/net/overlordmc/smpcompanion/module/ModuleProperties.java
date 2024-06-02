package net.overlordmc.smpcompanion.module;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import net.overlordmc.smpcompanion.SMPCompanion;
import net.overlordmc.smpcompanion.module.annotation.PropertyPath;
import net.overlordmc.smpcompanion.module.annotation.PropertyType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ModuleProperties {

    private static final String MODULE_DIRECTORY = "/modules";

    private final Default defaults;
    private final Module module;
    private final YamlDocument document;

    public ModuleProperties(SMPCompanion plugin, Module module, Object... properties) {
        this.defaults = new Default();
        this.module = module;
        try {
            this.document = YamlDocument.create(
                    new File(plugin.getDataFolder() + MODULE_DIRECTORY, module.getName() + ".yml"),
                    GeneralSettings.builder().setUseDefaults(false).build());
        } catch (IOException e) {
            throw new RuntimeException("Could not create a configuration file for module " + module.getName(), e);
        }
        loadProperties(new Default());
        Arrays.stream(properties).forEach(this::loadProperties);
        reload();
    }

    public ModuleProperties(SMPCompanion plugin, Module module) {
        this(plugin, module, new Object[0]);
    }

    public void save() {
        try {
            document.save();
        } catch (IOException ex) {
            throw new RuntimeException("Could not save module " + module.getName(), ex);
        }
    }

    public void reload() {
        try {
            save();
            document.reload();
        } catch (IOException ex) {
            throw new RuntimeException("Could not reload module " + module.getName(), ex);
        }
    }

    public void loadProperties(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(PropertyPath.class)) continue;

            String path = field.getDeclaredAnnotation(PropertyPath.class).value();
            try {
                if (document.contains(path)) {
                    if (field.isAnnotationPresent(PropertyType.class)) {
                        Class<?> type = field.getDeclaredAnnotation(PropertyType.class).value();
                        field.set(object, document.getAs(path, type));
                    } else {
                        field.set(object, document.get(path));
                    }
                } else {
                    document.set(path, field.get(object));
                }
            } catch (IllegalAccessException ex) {
                throw new RuntimeException("Could not load property '" + path + "' for module " + module.getName(), ex);
            }
        }
    }

    public Default getDefaults() {
        return defaults;
    }

    public static class Default {

        @PropertyPath("enabled")
        public boolean isEnabled = true;

    }

}

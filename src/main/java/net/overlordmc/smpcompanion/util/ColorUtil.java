package net.overlordmc.smpcompanion.util;

import de.themoep.minedown.MineDown;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.List;

public class ColorUtil {

    private ColorUtil() {}

    public static List<String> text(List<String> text) {
        return text.stream()
                   .map(ColorUtil::text)
                   .toList();
    }

    public static String text(String text) {
        return BaseComponent.toLegacyText(component(text));
    }

    public static List<BaseComponent[]> component(List<String> text) {
        return text.stream()
                   .map(ColorUtil::component)
                   .toList();
    }

    public static BaseComponent[] component(String text) {
        return new MineDown(text).toComponent();
    }

}

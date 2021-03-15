package dev.sllcoding.minestomairconditioner.utils;

import net.kyori.adventure.text.Component;

public class ComponentUtil {

    public static Component get(Component component) {
        Component comp = Component.text("[MinestomAirConditioner] ");
        return comp.append(component);
    }

}

package ru.quantum_emperor.eternal_information_locker.integration;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import snownee.jade.Jade;
import snownee.jade.api.config.IWailaConfig;
import snownee.jade.impl.config.WailaConfig;

public class JadeIntegration {
    public static void toggleOverlay(boolean state) {
        WailaConfig.ConfigGeneral general = ((WailaConfig) Jade.CONFIG.get()).getGeneral();
        if (general.shouldDisplayTooltip() == state)
            return;
        IWailaConfig.DisplayMode mode = general.getDisplayMode();
        if (mode == IWailaConfig.DisplayMode.TOGGLE) {
            general.setDisplayTooltip(state);
            Jade.CONFIG.save();
        }
    }
}

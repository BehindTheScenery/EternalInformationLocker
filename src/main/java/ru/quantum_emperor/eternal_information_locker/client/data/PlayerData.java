package ru.quantum_emperor.eternal_information_locker.client.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PlayerData {
    public static final PlayerData INSTANCE = new PlayerData();
    public boolean isLockedInformation = true;
}

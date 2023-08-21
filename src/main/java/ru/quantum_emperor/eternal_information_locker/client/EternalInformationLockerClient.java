package ru.quantum_emperor.eternal_information_locker.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.client.MinecraftClient;
import ru.quantum_emperor.eternal_information_locker.client.data.PlayerData;
import ru.quantum_emperor.eternal_information_locker.integration.JMIntegration;
import ru.quantum_emperor.eternal_information_locker.integration.JadeIntegration;
import ru.quantum_emperor.eternal_information_locker.network.NetworkMessage;

@Environment(EnvType.CLIENT)
public class EternalInformationLockerClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        NetworkMessage.registerS2CPackets();
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (PlayerData.INSTANCE.isLockedInformation &&
                    entity.getUuid().equals(MinecraftClient.getInstance().player.getUuid())) {
                JMIntegration.closeMinimap();
                JadeIntegration.toggleOverlay(false);
            }
        });
    }
}

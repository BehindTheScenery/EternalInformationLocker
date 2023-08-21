package ru.quantum_emperor.eternal_information_locker.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

import static ru.quantum_emperor.eternal_information_locker.EternalInformationLocker.MOD_ID;

public class NetworkMessage {
    public static final Identifier PERSISTENT = new Identifier(MOD_ID, "persistent");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(PERSISTENT, ClientPacket::receive);
    }
}

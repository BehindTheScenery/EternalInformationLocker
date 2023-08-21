package ru.quantum_emperor.eternal_information_locker.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import ru.quantum_emperor.eternal_information_locker.client.data.PlayerData;

@Environment(EnvType.CLIENT)
public class ClientPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        PlayerData.INSTANCE.isLockedInformation = buf.getBoolean(0);
    }

}

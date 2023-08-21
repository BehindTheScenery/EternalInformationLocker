package ru.quantum_emperor.eternal_information_locker;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.quantum_emperor.eternal_information_locker.data.ServerState;
import ru.quantum_emperor.eternal_information_locker.item.QuantumIdentifierItem;
import ru.quantum_emperor.eternal_information_locker.network.NetworkMessage;

public class EternalInformationLocker implements ModInitializer {
    public static final String MOD_ID = "eternal_information_locker";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // ITEM
    public static final Item QUANTUM_IDENTIFIER = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quantum_identifier"),
            new QuantumIdentifierItem(new FabricItemSettings().maxCount(1).group(ItemGroup.MISC)));

    @Override
    public void onInitialize() {
        ServerWorldEvents.LOAD.register(((server, world) -> {
            world.getPersistentStateManager().set(MOD_ID, ServerState.getServerState(server));
        }));


        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerState serverState = ServerState.getServerState(handler.player.world.getServer());
            serverState.players.putIfAbsent(handler.player.getUuid(), new ServerState.PlayerData());
            LOGGER.info(String.valueOf(serverState.players.get(handler.player.getUuid()).isLockedInformation));
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(serverState.players.get(handler.player.getUuid()).isLockedInformation);
            ServerPlayNetworking.send(handler.player, NetworkMessage.PERSISTENT,buf);
        });
    }
}

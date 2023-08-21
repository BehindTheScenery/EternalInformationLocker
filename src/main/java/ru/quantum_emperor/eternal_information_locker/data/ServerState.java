package ru.quantum_emperor.eternal_information_locker.data;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ru.quantum_emperor.eternal_information_locker.EternalInformationLocker.MOD_ID;

public class ServerState extends PersistentState {

    public Map<UUID, PlayerData> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersNbt = new NbtCompound();

        players.forEach((UUID, playerData) -> {
            NbtCompound playerDataAsNbt = new NbtCompound();

            playerDataAsNbt.putBoolean("isLockedInformation", playerData.isLockedInformation);

            playersNbt.put(String.valueOf(UUID), playerDataAsNbt);
        });
        nbt.put("EILplayers", playersNbt);

        return nbt;
    }

    public static ServerState createFromNbt(NbtCompound nbt) {
        ServerState serverState = new ServerState();

        NbtCompound playersTag = nbt.getCompound("EILplayers");
        playersTag.getKeys().forEach(key -> {
            PlayerData playerState = new PlayerData();

            playerState.isLockedInformation = playersTag.getCompound(key).getBoolean("isLockedInformation");

            UUID uuid = UUID.fromString(key);
            serverState.players.put(uuid, playerState);
        });
        return serverState;
    }

    public static ServerState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        return persistentStateManager.getOrCreate(
                ServerState::createFromNbt,
                ServerState::new,
                MOD_ID);
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        ServerState serverState = getServerState(player.world.getServer());
        return serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
    }

    public static class PlayerData {
        public boolean isLockedInformation = true;
    }
}

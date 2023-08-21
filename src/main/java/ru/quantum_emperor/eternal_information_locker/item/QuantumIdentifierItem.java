package ru.quantum_emperor.eternal_information_locker.item;

import journeymap.client.ui.UIManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import ru.quantum_emperor.eternal_information_locker.client.data.PlayerData;
import ru.quantum_emperor.eternal_information_locker.data.ServerState;
import ru.quantum_emperor.eternal_information_locker.integration.JMIntegration;
import ru.quantum_emperor.eternal_information_locker.integration.JadeIntegration;
import ru.quantum_emperor.eternal_information_locker.network.NetworkMessage;
import snownee.jade.Jade;

public class QuantumIdentifierItem extends Item {
    public QuantumIdentifierItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ServerState.getPlayerState(user).isLockedInformation = !ServerState.getPlayerState(user).isLockedInformation;
            ServerState.getServerState(((ServerWorld) world).getServer()).markDirty();
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(ServerState.getPlayerState(user).isLockedInformation);
            ServerPlayNetworking.send((ServerPlayerEntity) user, NetworkMessage.PERSISTENT, buf);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        PlayerData.INSTANCE.isLockedInformation = !PlayerData.INSTANCE.isLockedInformation;
        if (PlayerData.INSTANCE.isLockedInformation) {
            JMIntegration.closeMinimap();
            JMIntegration.toggleAllWaypoints(false);
            JadeIntegration.toggleOverlay(false);
        } else {
            JadeIntegration.toggleOverlay(true);
            JMIntegration.toggleAllWaypoints(true);
            UIManager.INSTANCE.toggleMinimap();
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

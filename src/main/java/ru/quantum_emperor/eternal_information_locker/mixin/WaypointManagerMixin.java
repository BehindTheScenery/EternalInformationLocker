package ru.quantum_emperor.eternal_information_locker.mixin;

import journeymap.client.ui.waypoint.WaypointManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.quantum_emperor.eternal_information_locker.client.data.PlayerData;

@Mixin(value = WaypointManager.class, remap = false)
public abstract class WaypointManagerMixin {
    @Inject(method = "toggleAllWaypoints", at = @At("HEAD"))
    private static void disableToToggleWaypoints(CallbackInfo ci) {
        if (PlayerData.INSTANCE.isLockedInformation) {
            if (MinecraftClient.getInstance().player != null)
                MinecraftClient.getInstance().player.sendMessage(
                        Text.translatable("message.eternal_information_locker.disabled_mod", "Journey map")
                );
            ci.cancel();
        }
    }
}

package ru.quantum_emperor.eternal_information_locker.mixin;

import journeymap.client.ui.UIManager;
import journeymap.client.ui.component.JmUI;
import journeymap.client.ui.fullscreen.Fullscreen;
import journeymap.client.waypoint.Waypoint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import ru.quantum_emperor.eternal_information_locker.client.data.PlayerData;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.quantum_emperor.eternal_information_locker.client.data.PlayerData;
import ru.quantum_emperor.eternal_information_locker.data.ServerState;

import static ru.quantum_emperor.eternal_information_locker.EternalInformationLocker.LOGGER;

@Mixin(value = UIManager.class, remap = false)
public abstract class UIManagerMixin {

    @Shadow
    MinecraftClient minecraft;

    @Inject(method = "toggleMinimap()V", at = @At("HEAD"), cancellable = true)
    private void closeMinimap(CallbackInfo ci) {
        if (PlayerData.INSTANCE.isLockedInformation) {
            if (MinecraftClient.getInstance().player != null)
                MinecraftClient.getInstance().player.sendMessage(
                        Text.translatable("message.eternal_information_locker.disabled_mod", "Journey map")
                );
            ci.cancel();
        }
    }

    @Inject(method = "openWaypointEditor",
            at = @At("HEAD"), cancellable = true)
    private void cancelCreateWaypoint(Waypoint waypoint, boolean isNew, JmUI returnDisplay, CallbackInfo ci) {
        if (PlayerData.INSTANCE.isLockedInformation) {
            if (MinecraftClient.getInstance().player != null)
                MinecraftClient.getInstance().player.sendMessage(
                        Text.translatable("message.eternal_information_locker.disabled_mod", "Journey map")
                );
            ci.cancel();
        }
    }

    @Inject(method = "openWaypointManager", at = @At("HEAD"), cancellable = true)
    private void closeCreateWaypoint(Waypoint waypoint, JmUI returnDisplay, CallbackInfo ci) {
        if (PlayerData.INSTANCE.isLockedInformation) {
            if (MinecraftClient.getInstance().player != null)
                MinecraftClient.getInstance().player.sendMessage(
                        Text.translatable("message.eternal_information_locker.disabled_mod", "Journey map")
                );
            ci.cancel();
        }
    }

    @Inject(method = "openFullscreenMap()Ljourneymap/client/ui/fullscreen/Fullscreen;", at = @At("HEAD"), cancellable = true)
    private void closeFullscreen(CallbackInfoReturnable<Fullscreen> cir) {
        if (PlayerData.INSTANCE.isLockedInformation) {
            if (MinecraftClient.getInstance().player != null)
                MinecraftClient.getInstance().player.sendMessage(
                        Text.translatable("message.eternal_information_locker.disabled_mod", "Journey map")
                );
            cir.setReturnValue(null);
        }
    }
}

package ru.quantum_emperor.eternal_information_locker.mixin.client;

import journeymap.client.render.ingame.WaypointRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.quantum_emperor.eternal_information_locker.client.data.PlayerData;

@Mixin(WaypointRenderer.class)
public abstract class WaypointRenderMixin {
    @Inject(method = "renderAllWaypoints(Lnet/minecraft/client/util/math/MatrixStack;Z)V", at = @At("HEAD"), cancellable = true)
    private void cancelRender(MatrixStack poseStack, boolean shaderBeacon, CallbackInfo ci) {
        if (PlayerData.INSTANCE.isLockedInformation) {
            ci.cancel();
        }
    }
}

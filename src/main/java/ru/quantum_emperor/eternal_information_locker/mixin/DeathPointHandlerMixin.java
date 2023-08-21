package ru.quantum_emperor.eternal_information_locker.mixin;

import journeymap.client.event.handlers.DeathPointHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.quantum_emperor.eternal_information_locker.client.data.PlayerData;

@Mixin(value = DeathPointHandler.class, remap = false)
public abstract class DeathPointHandlerMixin {

    @Inject(method = "createDeathpoint", at = @At("HEAD"), cancellable = true)
    private void cancelCreateDeathpoint(CallbackInfo ci) {
        if (PlayerData.INSTANCE.isLockedInformation) {
            ci.cancel();
        }
    }
}

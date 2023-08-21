package ru.quantum_emperor.eternal_information_locker.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.quantum_emperor.eternal_information_locker.client.data.PlayerData;
import snownee.jade.impl.config.WailaConfig;

@Mixin(value = WailaConfig.ConfigGeneral.class, remap = false)
public abstract class WailaConfigMixin {
    @Inject(method = "setDisplayTooltip(Z)V",
            at = @At("HEAD"), cancellable = true)
    private void cancelToggleDisplayTooltip(boolean state, CallbackInfo ci) {
        if (PlayerData.INSTANCE.isLockedInformation && state) {
            if (MinecraftClient.getInstance().player != null)
                MinecraftClient.getInstance().player.sendMessage(Text.translatable("message.eternal_information_locker.disabled_mod", "Jade"));
            ci.cancel();
        }
    }
}

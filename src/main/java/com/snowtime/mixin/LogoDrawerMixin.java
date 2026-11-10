package com.snowtime.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Прячем ванильный логотип MINECRAFT, вместо него рисуется SNOW TIME
@Mixin(LogoDrawer.class)
public class LogoDrawerMixin {
    @Inject(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V",
            at = @At("HEAD"), cancellable = true, require = 0)
    private void snowtime$hideLogo(DrawContext ctx, int screenWidth, float alpha, int y, CallbackInfo ci) {
        ci.cancel();
    }
}

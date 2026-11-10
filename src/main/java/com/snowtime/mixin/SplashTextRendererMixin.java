package com.snowtime.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Прячем жёлтую надпись-сплэш
@Mixin(SplashTextRenderer.class)
public class SplashTextRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true, require = 0)
    private void snowtime$hideSplash(DrawContext ctx, int screenWidth, TextRenderer tr, int alpha, CallbackInfo ci) {
        ci.cancel();
    }
}

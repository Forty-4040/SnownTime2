package com.snowtime.mixin;

import com.snowtime.FrozenRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PressableWidget.class)
public abstract class PressableWidgetMixin {
    @Inject(method = "renderButton", at = @At("HEAD"), cancellable = true)
    private void snowtime$frozen(DrawContext ctx, int mx, int my, float delta, CallbackInfo ci) {
        FrozenRenderer.draw(ctx, (ClickableWidget) (Object) this);
        ci.cancel();
    }
}

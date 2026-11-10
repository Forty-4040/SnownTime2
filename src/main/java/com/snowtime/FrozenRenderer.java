package com.snowtime;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;

public class FrozenRenderer {

    public static void draw(DrawContext c, ClickableWidget b) {
        int x = b.getX(), y = b.getY(), w = b.getWidth(), h = b.getHeight();
        boolean off = !b.active;
        boolean hov = b.isHovered() && !off;

        int top = off ? 0xC0707F8C : hov ? 0xE0BFEFFF : 0xD08FCBEA;
        int bot = off ? 0xC0556270 : hov ? 0xE06FB8E8 : 0xD04F8FC0;

        // ледяное тело
        c.fillGradient(x, y, x + w, y + h, top, bot);
        // иней и блик
        c.fill(x + 1, y + 1, x + w - 1, y + 2, 0xB0FFFFFF);
        c.fill(x + 1, y + 1, x + 2, y + h - 1, 0x60FFFFFF);
        c.drawBorder(x, y, w, h, hov ? 0xFFFFFFFF : 0xFFD8F4FF);

        if (w >= 40) {
            int seed = x * 31 + y * 17;
            // трещинки
            c.fill(x + 12 + (seed & 15), y + 3, x + 13 + (seed & 15), y + 8, 0x40FFFFFF);
            c.fill(x + w - 28 - (seed & 7), y + h - 8, x + w - 27 - (seed & 7), y + h - 3, 0x40FFFFFF);
            // сосульки снизу
            for (int px = x + 4; px < x + w - 4; px += 7) {
                int len = 2 + (((px * 7919 + seed) & 0xFF) % 3) + (hov ? 1 : 0);
                for (int k = 0; k < len; k++) {
                    int half = Math.max(0, (len - k) / 2);
                    c.fill(px - half, y + h + k, px + half + 1, y + h + k + 1, 0xC8DFF6FF);
                }
            }
        }

        int color = off ? 0xFFA0A0A0 : hov ? 0xFFFFFFFF : 0xFFE8F8FF;
        c.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer,
        b.getMessage(), x + w / 2, y + (h - 8) / 2, color);
    }
}

package com.snowtime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;

import java.util.Random;

public class SnowTimeClient implements ClientModInitializer {
    public static final String MOD_ID = "snowtime";

    /** Лёгкий снег поверх вашего видео-фона. Поставьте false, если не нужен. */
    public static final boolean SNOW_OVERLAY = true;

    private static final String TITLE = "SNOW TIME";
    private static final long START = System.currentTimeMillis();

    private static final int FLAKES = 120;
    private static final float[] BX = new float[FLAKES], BY = new float[FLAKES],
            SPEED = new float[FLAKES], AMP = new float[FLAKES],
            FREQ = new float[FLAKES], PHASE = new float[FLAKES];
    private static final int[] SIZE = new int[FLAKES];

    static {
        Random r = new Random(1337);
        for (int i = 0; i < FLAKES; i++) {
            BX[i] = r.nextFloat();
            BY[i] = r.nextFloat();
            int layer = r.nextInt(3);
            SIZE[i] = 1 + layer;
            SPEED[i] = 0.02f + layer * 0.03f + r.nextFloat() * 0.015f;
            AMP[i] = 6 + layer * 8 + r.nextFloat() * 6;
            FREQ[i] = 0.4f + r.nextFloat() * 0.8f;
            PHASE[i] = r.nextFloat() * 6.28f;
        }
    }

    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, w, h) -> {
            if (screen instanceof TitleScreen) {
                ScreenEvents.afterRender(screen).register((s, ctx, mx, my, delta) -> {
                    float t = (System.currentTimeMillis() - START) / 1000f;
                    if (SNOW_OVERLAY) drawSnow(ctx, s.width, s.height, t);
                    drawTitle(ctx, s.width, t);
                });
            }
        });
    }

    private static void drawSnow(DrawContext c, int width, int height, float t) {
        for (int i = 0; i < FLAKES; i++) {
            float y = ((BY[i] + t * SPEED[i]) % 1f) * (height + 10) - 5;
            float x = BX[i] * width + (float) Math.sin(t * FREQ[i] + PHASE[i]) * AMP[i];
            x = ((x % width) + width) % width;
            int s = SIZE[i];
            int a = 90 + (s - 1) * 60;
            int ix = (int) x, iy = (int) y;
            c.fill(ix, iy, ix + s, iy + s, (a << 24) | 0xFFFFFF);
        }
    }

    private static void drawTitle(DrawContext c, int width, float t) {
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        float scale = 5f;
        var m = c.getMatrices();
        m.push();
        m.translate(width / 2f, 28, 0);
        m.scale(scale, scale, 1f);

        int tw = tr.getWidth(TITLE);
        float glow = 0.5f + 0.5f * (float) Math.sin(t * 1.5);
        int gcol = ((int) (40 + 40 * glow) << 24) | 0x7FD6FF;
        for (int dx = -1; dx <= 1; dx++)
            for (int dy = -1; dy <= 1; dy++)
                if (dx != 0 || dy != 0)
                    c.drawText(tr, TITLE, -tw / 2 + dx, dy, gcol, false);

        int xx = -tw / 2;
        for (int i = 0; i < TITLE.length(); i++) {
            String ch = String.valueOf(TITLE.charAt(i));
            float k = 0.5f + 0.5f * (float) Math.sin(t * 2f + i * 0.7f);
            int r = (int) (190 + 65 * k), g = (int) (230 + 25 * k);
            c.drawText(tr, ch, xx, 0, 0xFF000000 | (r << 16) | (g << 8) | 0xFF, true);
            xx += tr.getWidth(ch);
        }
        m.pop();
    }
}

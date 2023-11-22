package com.flowng.attack;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import static net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback.EVENT;

public class AttackOverlay implements HudRenderCallback {
	MinecraftClient mc = MinecraftClient.getInstance();

	@Override
	public void onHudRender(MatrixStack matrices, float tickDelta) {
		final Window win = mc.getWindow();
		if (!Activate.Attack){
			return;
		}
		if (Activate.Attack) {
			DrawableHelper.fill(matrices, 0, 0, win.getWidth(), win.getHeight(), 0x1F00FF00);
		}
	}
}

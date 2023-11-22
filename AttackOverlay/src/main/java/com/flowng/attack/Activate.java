package com.flowng.attack;

import net.minecraft.client.MinecraftClient;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.fabricmc.api.EnvType;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.AxeItem;
import net.minecraft.entity.player.PlayerEntity;
import java.util.Random;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
@Environment(EnvType.CLIENT)
public class Activate implements ClientModInitializer {
    private boolean isValidItem(Item item) {
        return item instanceof SwordItem || item instanceof AxeItem;
    }

    MinecraftClient mc = MinecraftClient.getInstance();
    private Entity getTarget() {
        HitResult hitResult = mc.crosshairTarget;

        if(hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            return entityHitResult.getEntity();
        }

        return null;
    }
    public static boolean Attack = false;
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new AttackOverlay());
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().player != null) {
                if (!isValidItem(MinecraftClient.getInstance().player.getMainHandStack().getItem())) {
                    Attack = false;
                    return;
                }
                if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult)client.crosshairTarget).getEntity();
                    if (entity.isAlive() && entity.isAttackable() && entity instanceof PlayerEntity && client.player.getAttackCooldownProgress(0) >= 0.9) {
                        Attack = true;
                    } else {
                        Attack = false;
                        return;
                    }
                }
                if (client.crosshairTarget != null && client.crosshairTarget.getType() != HitResult.Type.ENTITY){
                    Attack = false;
                    return;
                }
            }
        });
    }
}

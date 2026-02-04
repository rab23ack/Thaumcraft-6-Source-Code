package thaumcraft.warp;

import java.util.Random;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public final class WarpManager {
    private static final int TICK_INTERVAL = 2000;

    private WarpManager() {
    }

    public static WarpData get(ServerPlayerEntity player) {
        return WarpState.get((ServerWorld) player.getWorld()).getOrCreate(player.getUuid());
    }

    public static void addWarp(ServerPlayerEntity player, WarpType type, int amount) {
        WarpData data = get(player);
        switch (type) {
            case PERMANENT -> data.addPermanent(amount);
            case NORMAL -> data.addNormal(amount);
            case TEMPORARY -> data.addTemporary(amount);
        }
        markDirty(player);
    }

    public static void setWarp(ServerPlayerEntity player, WarpType type, int amount) {
        WarpData data = get(player);
        switch (type) {
            case PERMANENT -> data.setPermanent(amount);
            case NORMAL -> data.setNormal(amount);
            case TEMPORARY -> data.setTemporary(amount);
        }
        markDirty(player);
    }

    public static void tickPlayer(ServerPlayerEntity player) {
        if (player.age % TICK_INTERVAL != 0) {
            return;
        }
        WarpData data = get(player);
        if (data.getTemporary() > 0) {
            data.addTemporary(-1);
        }
        int total = Math.min(100, data.getTotal());
        if (total <= 0) {
            markDirty(player);
            return;
        }
        data.setCounter(data.getCounter() + total);
        applyWarpEvent(player, data, total);
        markDirty(player);
    }

    private static void applyWarpEvent(ServerPlayerEntity player, WarpData data, int total) {
        Random random = player.getRandom();
        int chance = MathHelper.clamp(total, 1, 100);
        if (random.nextInt(100) >= chance) {
            return;
        }
        int roll = random.nextInt(100);
        if (roll < 30) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
            player.sendMessage(Text.translatable("warp.text.nausea"), true);
        } else if (roll < 55) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 0));
            player.sendMessage(Text.translatable("warp.text.hunger"), true);
        } else if (roll < 75) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 200, 0));
            player.sendMessage(Text.translatable("warp.text.darkness"), true);
        } else if (roll < 90) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 0));
            player.sendMessage(Text.translatable("warp.text.blindness"), true);
        } else {
            data.addNormal(-1);
            player.sendMessage(Text.translatable("warp.text.fade"), true);
        }
    }

    private static void markDirty(ServerPlayerEntity player) {
        WarpState.get((ServerWorld) player.getWorld()).markDirty();
    }
}

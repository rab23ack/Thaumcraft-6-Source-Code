package thaumcraft.warp;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public final class WarpBootstrap {
    private WarpBootstrap() {
    }

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> server.getPlayerManager().getPlayerList()
                .forEach(WarpManager::tickPlayer));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                CommandManager.literal("thaumcraft")
                        .then(CommandManager.literal("warp")
                                .requires(source -> source.hasPermissionLevel(2))
                                .then(CommandManager.literal("add")
                                        .then(CommandManager.argument("type", StringArgumentType.word())
                                                .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                                        .executes(ctx -> handleWarp(
                                                                ctx.getSource(),
                                                                StringArgumentType.getString(ctx, "type"),
                                                                IntegerArgumentType.getInteger(ctx, "amount"),
                                                                true))))))
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.argument("type", StringArgumentType.word())
                                                .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                                        .executes(ctx -> handleWarp(
                                                                ctx.getSource(),
                                                                StringArgumentType.getString(ctx, "type"),
                                                                IntegerArgumentType.getInteger(ctx, "amount"),
                                                                false)))))))));
    }

    private static int handleWarp(ServerCommandSource source, String typeRaw, int amount, boolean add) {
        ServerPlayerEntity player = source.getPlayer();
        if (player == null) {
            source.sendError(Text.literal("Player context required."));
            return 0;
        }
        WarpType type = parseType(typeRaw);
        if (type == null) {
            source.sendError(Text.literal("Unknown warp type: " + typeRaw + ". Use normal|permanent|temporary."));
            return 0;
        }
        if (add) {
            WarpManager.addWarp(player, type, amount);
            source.sendFeedback(() -> Text.translatable("warp.command.add", amount, type.name().toLowerCase()), true);
        } else {
            WarpManager.setWarp(player, type, amount);
            source.sendFeedback(() -> Text.translatable("warp.command.set", amount, type.name().toLowerCase()), true);
        }
        WarpData data = WarpManager.get(player);
        source.sendFeedback(() -> Text.translatable("warp.command.status", data.getNormal(), data.getPermanent(), data.getTemporary()), false);
        return 1;
    }

    private static WarpType parseType(String typeRaw) {
        return switch (typeRaw.toLowerCase()) {
            case "normal" -> WarpType.NORMAL;
            case "permanent", "perm" -> WarpType.PERMANENT;
            case "temporary", "temp" -> WarpType.TEMPORARY;
            default -> null;
        };
    }
}

package thaumcraft.warp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class WarpState extends PersistentState {
    private static final String ID = "thaumcraft_warp";
    private final Map<UUID, WarpData> warp = new HashMap<>();

    public WarpData getOrCreate(UUID playerId) {
        return warp.computeIfAbsent(playerId, id -> new WarpData());
    }

    public static WarpState get(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        return manager.getOrCreate(WarpState::fromNbt, WarpState::new, ID);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList list = new NbtList();
        for (Map.Entry<UUID, WarpData> entry : warp.entrySet()) {
            WarpData data = entry.getValue();
            NbtCompound tag = new NbtCompound();
            tag.putUuid("player", entry.getKey());
            tag.putInt("normal", data.getNormal());
            tag.putInt("permanent", data.getPermanent());
            tag.putInt("temporary", data.getTemporary());
            tag.putInt("counter", data.getCounter());
            list.add(tag);
        }
        nbt.put("warp", list);
        return nbt;
    }

    public static WarpState fromNbt(NbtCompound nbt) {
        WarpState state = new WarpState();
        NbtList list = nbt.getList("warp", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            NbtCompound tag = list.getCompound(i);
            if (!tag.containsUuid("player")) {
                continue;
            }
            WarpData data = new WarpData();
            data.setNormal(tag.getInt("normal"));
            data.setPermanent(tag.getInt("permanent"));
            data.setTemporary(tag.getInt("temporary"));
            data.setCounter(tag.getInt("counter"));
            state.warp.put(tag.getUuid("player"), data);
        }
        return state;
    }
}

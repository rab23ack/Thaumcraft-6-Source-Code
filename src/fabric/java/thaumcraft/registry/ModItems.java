package thaumcraft.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import thaumcraft.ThaumcraftFabric;

public final class ModItems {
    public static final Item ARCANE_CRYSTAL = registerItem("arcane_crystal", new Item(new Item.Settings()));

    private ModItems() {
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ThaumcraftFabric.MOD_ID, name), item);
    }

    public static void register() {
        ThaumcraftFabric.LOGGER.info("Registering items for {}", ThaumcraftFabric.MOD_ID);
    }
}

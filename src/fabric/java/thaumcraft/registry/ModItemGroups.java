package thaumcraft.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thaumcraft.ThaumcraftFabric;

public final class ModItemGroups {
    public static final ItemGroup THAUMCRAFT_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(ThaumcraftFabric.MOD_ID, "main"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.thaumcraft"))
                    .icon(() -> new ItemStack(ModItems.ARCANE_CRYSTAL))
                    .entries((context, entries) -> {
                        entries.add(ModItems.ARCANE_CRYSTAL);
                        entries.add(ModBlocks.ARCANE_BLOCK);
                    })
                    .build()
    );

    private ModItemGroups() {
    }

    public static void register() {
        ThaumcraftFabric.LOGGER.info("Registering item groups for {}", ThaumcraftFabric.MOD_ID);
    }
}

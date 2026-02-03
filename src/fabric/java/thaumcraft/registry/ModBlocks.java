package thaumcraft.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import thaumcraft.ThaumcraftFabric;

public final class ModBlocks {
    public static final Block ARCANE_BLOCK = registerBlock(
            "arcane_block",
            new Block(AbstractBlock.Settings.copy(Blocks.STONE).strength(1.5f, 6.0f).requiresTool())
    );

    private ModBlocks() {
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ThaumcraftFabric.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(
                Registries.ITEM,
                new Identifier(ThaumcraftFabric.MOD_ID, name),
                new BlockItem(block, new Item.Settings())
        );
    }

    public static void register() {
        ThaumcraftFabric.LOGGER.info("Registering blocks for {}", ThaumcraftFabric.MOD_ID);
    }
}

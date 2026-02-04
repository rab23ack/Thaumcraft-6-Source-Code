package thaumcraft;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thaumcraft.registry.ModBlocks;
import thaumcraft.registry.ModItemGroups;
import thaumcraft.registry.ModItems;
import thaumcraft.warp.WarpBootstrap;

public class ThaumcraftFabric implements ModInitializer {
    public static final String MOD_ID = "thaumcraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Thaumcraft Fabric port scaffolding.");
        ModBlocks.register();
        ModItems.register();
        ModItemGroups.register();
        WarpBootstrap.init();
    }
}

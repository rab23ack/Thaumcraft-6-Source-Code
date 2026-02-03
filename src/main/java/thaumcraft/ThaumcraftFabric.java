package thaumcraft;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThaumcraftFabric implements ModInitializer {
    public static final String MOD_ID = "thaumcraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Thaumcraft Fabric port scaffolding.");
    }
}

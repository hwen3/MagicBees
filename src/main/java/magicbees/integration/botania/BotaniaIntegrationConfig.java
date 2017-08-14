package magicbees.integration.botania;

import magicbees.util.ConfigHandler;
import magicbees.util.IConfiguration;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by Elec332 on 18-5-2017.
 */
public class BotaniaIntegrationConfig implements IConfiguration {

	public static float hiveacynthPrincessSpawnRate = 0.09f;

	public static float hiveacynthPristineRate = 0.15f;

	public static float hiveacynthManaMultiplier = 1.0f;

	public static float hiveacynthRainResistRate = 0.1f;

	public static float hibeescusTicksMultiplier = 1.0f;

	public static float hibeescusManaCostMultiplier = 1.0f;

	public static float beegoniaManaMultiplier = 1.0f;

	@Override
	public void init(ConfigHandler configHandler) {

	}

	@Override
	public void reload(Configuration config) {

	}
}

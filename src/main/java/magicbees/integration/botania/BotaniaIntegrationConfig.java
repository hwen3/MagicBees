package magicbees.integration.botania;

import magicbees.api.module.IConfiguration;
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

    private static final String CATEGORY_BOTANIA = "botania";

    @Override
    public void reload(Configuration config) {
        hiveacynthPrincessSpawnRate = config.getFloat("hiveacynthPrincessSpawnRate", CATEGORY_BOTANIA, 0.09f, 0, 1, "Sets hiveacynt princess spawn rate.");
        hiveacynthPristineRate = config.getFloat("hiveacynthPristineRate", CATEGORY_BOTANIA, 0.15f, 0, 1, "Sets hiveacynt pristine chance.");
        hiveacynthManaMultiplier = config.getFloat("hiveacynthManaMultiplier", CATEGORY_BOTANIA, 1.0f, 0, 1, "Sets hiveacynt mana multiplier.");
        hiveacynthRainResistRate = config.getFloat("hiveacynthRainResistRate", CATEGORY_BOTANIA, 0.1f, 0, 1, "Sets the chance a  hiveacynt will spawn bees with rain-resistance.");

        hibeescusTicksMultiplier = config.getFloat("hibeescusTicksMultiplier", CATEGORY_BOTANIA, 1.0f, 0, 2, "Sets the hibeescus tick multiplier.");
        hibeescusManaCostMultiplier = config.getFloat("hibeescusManaCostMultiplier", CATEGORY_BOTANIA, 1.0f, 0, 16, "Sets the hibeescus mana cost multiplier.");

        beegoniaManaMultiplier = config.getFloat("beegoniaManaMultiplier", CATEGORY_BOTANIA, 1.0f, 0, 16, "Sets the beegonia mana multiplier.");
    }

}

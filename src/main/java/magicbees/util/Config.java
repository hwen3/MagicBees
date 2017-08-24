package magicbees.util;


import magicbees.api.module.IConfiguration;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by Elec332 on 4-3-2017.
 */
public class Config implements IConfiguration {

    public static boolean magnetSound = true;

    public static int magnetMaxLevel = 9;

    public static float magnetBaseRange = 3;

    public static float magnetLevelMultiplier = 0.75f;

    public static boolean moonDialShowsPhaseInText = true;

    public static boolean fancyJarRenderer = true;

    @Override
    public void reload(Configuration config) {
        magnetSound = config.getBoolean("magnetSound", Configuration.CATEGORY_GENERAL, true, "Enables/Disables the magnet sounds.");
        magnetMaxLevel = config.getInt("magnetMaxLevel", Configuration.CATEGORY_GENERAL, 9, 1, 16, "Sets max level of the Mysterious Magnet.");
        magnetBaseRange = config.getInt("magnetBaseRange", Configuration.CATEGORY_GENERAL, 3, 1, 8, "Sets base range of the Mysterious Magnet.");
        magnetLevelMultiplier = config.getFloat("magnetLevelMultiplier", Configuration.CATEGORY_GENERAL, 0.75f, 0.1f, 8,"Used to calculate the range of the Mysterious Magnet; Range = baseValue + level * multiplier");
        moonDialShowsPhaseInText = config.getBoolean("moonDialShowsPhaseInText", Configuration.CATEGORY_GENERAL, true, "Whether the MoonDial should show the MoonPhase in it's tooltip.");
        fancyJarRenderer = config.getBoolean("fancyJarRenderer", Configuration.CATEGORY_CLIENT, true, "Whether to render the bee inside the Bee Collector's Jar with a TESR.");
    }

    //@Configurable.Class(comment = "Here you can configure certain bees")
    public static class Bees {

        public static boolean enableGemBees = true;

    }

}

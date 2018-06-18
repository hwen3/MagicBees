package magicbees.util;


import magicbees.api.module.IConfigRegistry;
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

    public static boolean oldJarModel = false;

    public static boolean showAllBees = true;

    public static boolean removeUnneededBees = true;

    @Override
    public void init(IConfigRegistry configHandler) {
        configHandler.registerCategoryComment(Configuration.CATEGORY_GENERAL, "General settings");
        configHandler.registerCategoryComment(Configuration.CATEGORY_CLIENT, "Client settings");
        configHandler.registerConfig(new ConfigWorldGen());
    }

    @Override
    public void reload(Configuration config) {
        magnetSound = config.getBoolean("magnetSound", Configuration.CATEGORY_GENERAL, magnetSound, "Enables/Disables the magnet sounds.");
        magnetMaxLevel = config.getInt("magnetMaxLevel", Configuration.CATEGORY_GENERAL, 9, 1, 16, "Sets max level of the Mysterious Magnet.");
        magnetBaseRange = config.getInt("magnetBaseRange", Configuration.CATEGORY_GENERAL, 3, 1, 8, "Sets base range of the Mysterious Magnet.");
        magnetLevelMultiplier = config.getFloat("magnetLevelMultiplier", Configuration.CATEGORY_GENERAL, 0.75f, 0.1f, 8,"Used to calculate the range of the Mysterious Magnet; Range = baseValue + level * multiplier");
        moonDialShowsPhaseInText = config.getBoolean("moonDialShowsPhaseInText", Configuration.CATEGORY_GENERAL, moonDialShowsPhaseInText, "Whether the MoonDial should show the MoonPhase in it's tooltip.");
        fancyJarRenderer = config.getBoolean("fancyJarRenderer", Configuration.CATEGORY_CLIENT, fancyJarRenderer, "Whether to render the bee inside the Bee Collector's Jar with a TESR.");
        oldJarModel = config.getBoolean("oldJarmodel", Configuration.CATEGORY_CLIENT, oldJarModel, "Set to true to use the old model for the Bee Collector's Jar");
        showAllBees = config.getBoolean("showAllBees", Configuration.CATEGORY_GENERAL, showAllBees, "Set to true to show all MagicBees bees in the creative tab/JEI.");
        removeUnneededBees = config.getBoolean("removeUnneededBees", Configuration.CATEGORY_GENERAL, removeUnneededBees, "When true, removes all bees related to mods that are not present from the game.");
    }

    //@Configurable.Class(comment = "Here you can configure certain bees")
    public static class Bees {

        public static boolean enableGemBees = true;

    }

}

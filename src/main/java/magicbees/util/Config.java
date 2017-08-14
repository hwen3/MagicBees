package magicbees.util;


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

    @Override
    public void init(ConfigHandler configHandler) {

    }

    @Override
    public void reload(Configuration config) {

    }

    //@Configurable.Class(comment = "Here you can configure certain bees")
    public static class Bees {


        public static boolean enableGemBees = true;

    }

}

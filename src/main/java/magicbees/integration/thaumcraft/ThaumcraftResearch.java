package magicbees.integration.thaumcraft;

import magicbees.util.MagicBeesResourceLocation;
import thaumcraft.api.ThaumcraftApi;

/**
 * Created by Elec332 on 17-8-2019
 */
public class ThaumcraftResearch {

    public static final String CATEGORY = "MAGICBEES";

    static void setupResearch() {
        //ResearchCategories.registerCategory(CATEGORY, );
        ThaumcraftApi.registerResearchLocation(new MagicBeesResourceLocation("research/magicbees"));
    }

}
